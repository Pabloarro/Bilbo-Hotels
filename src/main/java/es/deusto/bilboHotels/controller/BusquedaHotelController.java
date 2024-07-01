package es.deusto.bilboHotels.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.deusto.bilboHotels.model.dto.HotelBusquedaDTO;
import es.deusto.bilboHotels.model.dto.HotelDisponibilidadDTO;
import es.deusto.bilboHotels.service.ServicioBusquedaHotel;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BusquedaHotelController {

    private final ServicioBusquedaHotel servicioBusquedaHotel;

    @GetMapping("/search")
    public String showSearchForm(@ModelAttribute("hotelBusquedaDTO") HotelBusquedaDTO hotelBusquedaDTO) {
        return "hotelsearch/search";
    }


    @PostMapping("/search")
    public String findAvailableHotelsByCityAndDate(@Valid @ModelAttribute("hotelBusquedaDTO") HotelBusquedaDTO hotelBusquedaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "hotelsearch/search";
        }
        try {
            validateFechasCheckinYCheckout(hotelBusquedaDTO.getFechaCheckIn(), hotelBusquedaDTO.getFechaCheckOut());
        } catch (IllegalArgumentException e) {
            result.rejectValue("checkoutDate", null, e.getMessage());
            return "hotelsearch/search";
        }

        // Redirect to a new GET endpoint with parameters for data fetching. Allows page refreshing
        return String.format("redirect:/search-results?ciudad=%s&fechaCheckIn=%s&fechaCheckOut=%s", hotelBusquedaDTO.getCiudad(), hotelBusquedaDTO.getFechaCheckIn(), hotelBusquedaDTO.getFechaCheckOut());
    }

    @GetMapping("/search-results")
public String showSearchResults(@RequestParam String ciudad, @RequestParam String fechaCheckIn, @RequestParam String fechaCheckOut, Model model, RedirectAttributes redirectAttributes) {
    try {
        LocalDate parsedFechaCheckIn = LocalDate.parse(fechaCheckIn);
        LocalDate parsedFechaCheckOut = LocalDate.parse(fechaCheckOut);

        validateFechasCheckinYCheckout(parsedFechaCheckIn, parsedFechaCheckOut);

        log.info("Buscando hoteles para la ciudad {} entre las fechas {} y {}.", ciudad, fechaCheckIn, fechaCheckOut);
        List<HotelDisponibilidadDTO> hotels = servicioBusquedaHotel.buscarHotelesDisponiblesByCiudadAndFecha(ciudad, parsedFechaCheckIn, parsedFechaCheckOut);

        if (hotels.isEmpty()) {
            model.addAttribute("noHotelsFound", true);
        }

        long duracionDias = ChronoUnit.DAYS.between(parsedFechaCheckIn, parsedFechaCheckOut);

        model.addAttribute("hotels", hotels);
        model.addAttribute("ciudad", ciudad);  // Asegúrate de que este atributo se agrega al modelo
        model.addAttribute("dias", duracionDias);
        model.addAttribute("fechaCheckIn", fechaCheckIn);
        model.addAttribute("fechaCheckOut", fechaCheckOut);

    } catch (DateTimeParseException e) {
        log.error("Se proporcionó un formato de fecha no válido para la búsqueda en la URL.", e);
        redirectAttributes.addFlashAttribute("errorMessage", "Formato de fecha no válido. Por favor, utiliza el formulario de búsqueda.");
        return "redirect:/search";
    } catch (IllegalArgumentException e) {
        log.error("Se proporcionaron argumentos no válidos para la búsqueda en la URL", e);
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/search";
    } catch (Exception e) {
        log.error("Se produjo un error al buscar hoteles.", e);
        redirectAttributes.addFlashAttribute("errorMessage", "Ocurrió un error inesperado. Por favor, inténtalo de nuevo más tarde.");
        return "redirect:/search";
    }

    return "hotelsearch/search-results";
}


    @GetMapping("/hotel-details/{id}")
    public String showHotelDetails(@PathVariable Long id, @RequestParam String fechaCheckIn, @RequestParam String fechaCheckOut, Model model, RedirectAttributes redirectAttributes) {
        try {
            LocalDate parsedFechaCheckIn = LocalDate.parse(fechaCheckIn);
            LocalDate parsedFechaCheckOut = LocalDate.parse(fechaCheckOut);

            validateFechasCheckinYCheckout(parsedFechaCheckIn, parsedFechaCheckOut);

            HotelDisponibilidadDTO hotelDisponibilidadDTO = servicioBusquedaHotel.buscarHotelDisponibleById(id, parsedFechaCheckIn, parsedFechaCheckOut);

            long duracionDias = ChronoUnit.DAYS.between(parsedFechaCheckIn, parsedFechaCheckOut);

            model.addAttribute("hotel", hotelDisponibilidadDTO);
            model.addAttribute("duracionDias", duracionDias);
            model.addAttribute("fechaCheckIn", fechaCheckIn);
            model.addAttribute("fechaCheckOut", fechaCheckOut);

            return "hotelsearch/hotel-details";


        } catch (DateTimeParseException e) {
            log.error("Se proporcionó un formato de fecha no válido.", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Formato de fecha no válido. Por favor, utiliza el formulario de búsqueda.");
            return "redirect:/search";
        } catch (IllegalArgumentException e) {
            log.error("Se proporcionaron argumentos no válidos para la búsqueda en la URL.", e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/search";
        } catch (EntityNotFoundException e) {
            log.error("No se encontró ningún hotel con el ID {}.", id);
            redirectAttributes.addFlashAttribute("errorMessage", "El hotel seleccionado ya no está disponible. Por favor, inicia una nueva búsqueda.");
            return "redirect:/search";
        } catch (Exception e) {
            log.error("Se produjo un error al buscar hoteles.", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Se ha producido un error inesperado. Por favor, inténtalo de nuevo más tarde.");
            return "redirect:/search";
        }
    }

    private void validateFechasCheckinYCheckout(LocalDate fechaCheckIn, LocalDate checkoutDate) {
        if (fechaCheckIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de entrada no puede estar en el pasado.");
        }
        if (checkoutDate.isBefore(fechaCheckIn.plusDays(1))) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.");
        }
    }

    private void parseAndValidateBookingDates(String fechaCheckIn, String checkoutDate) {
        LocalDate parsedfechaCheckIn = LocalDate.parse(fechaCheckIn);
        LocalDate parsedCheckoutDate = LocalDate.parse(checkoutDate);
        validateFechasCheckinYCheckout(parsedfechaCheckIn, parsedCheckoutDate);
    }

}