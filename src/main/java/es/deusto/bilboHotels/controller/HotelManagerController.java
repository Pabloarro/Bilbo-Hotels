package es.deusto.bilboHotels.controller;

import es.deusto.bilboHotels.exception.HotelYaExisteException;
import es.deusto.bilboHotels.model.dto.ReservaDTO;
import es.deusto.bilboHotels.model.enums.TipoHabitacion;
import es.deusto.bilboHotels.model.dto.HotelDTO;
import es.deusto.bilboHotels.model.dto.HotelRegistroDTO;
import es.deusto.bilboHotels.model.dto.HabitacionDTO;
import es.deusto.bilboHotels.service.ServicioReserva;
import es.deusto.bilboHotels.service.ServicioHotel;
import es.deusto.bilboHotels.service.ServicioUsuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
@Slf4j
public class HotelManagerController {

    private final ServicioHotel servicioHotel;
    private final ServicioUsuario servicioUsuario;
    private final ServicioReserva servicioReserva;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "hotelmanager/dashboard";
    }

    @GetMapping("/hotels/add")
    public String showAddHotelForm(Model model) {
        HotelRegistroDTO hotelRegistroDTO = new HotelRegistroDTO();

        // Initialize roomDTOs with SINGLE and DOUBLE room types
        HabitacionDTO habitacionIndividual = new HabitacionDTO(null, null, TipoHabitacion.INDIVIDUAL, 0, 0.0);
        HabitacionDTO habitacionDoble = new HabitacionDTO(null, null, TipoHabitacion.DOBLE, 0, 0.0);
        hotelRegistroDTO.getHabitacionDTOs().add(habitacionIndividual);
        hotelRegistroDTO.getHabitacionDTOs().add(habitacionDoble);

        model.addAttribute("hotel", hotelRegistroDTO);
        return "hotelmanager/hotels-add";
    }

    @PostMapping("/hotels/add")
    public String añadirHotel(@Valid @ModelAttribute("hotel") HotelRegistroDTO hotelRegistroDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            log.warn("La creación del hotel falló debido a errores de validación.: {}", result.getAllErrors());
            return "hotelmanager/hotels-add";
        }
        try {
            servicioHotel.guardarHotel(hotelRegistroDTO);
            redirectAttributes.addFlashAttribute("message", "Hotel (" + hotelRegistroDTO.getNombre() + ") añadido satisfactoriamente");
            return "redirect:/manager/hotels";
        } catch (HotelYaExisteException e) {
            result.rejectValue("nombre", "hotel.exists", e.getMessage());
            return "hotelmanager/hotels-add";
        }
    }

    @GetMapping("/hotels")
    public String listHotels(Model model) {
        Long managerId = getIdManagerActual();
        List<HotelDTO> hotelList = servicioHotel.buscarTodosHotelDtosByManagerId(managerId);
        model.addAttribute("hotels", hotelList);
        return "hotelmanager/hotels";
    }

    @GetMapping("/hotels/edit/{id}")
    public String showEditHotelForm(@PathVariable Long id, Model model) {
        Long managerId = getIdManagerActual();
        HotelDTO hotelDTO = servicioHotel.buscarHotelByIdAndManagerId(id, managerId);
        model.addAttribute("hotel", hotelDTO);
        return "hotelmanager/hotels-edit";
    }

    @PostMapping("/hotels/edit/{id}")
    public String editarHotel(@PathVariable Long id, @Valid @ModelAttribute("hotel") HotelDTO hotelDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "hotelmanager/hotels-edit";
        }
        try {
            Long managerId = getIdManagerActual();
            hotelDTO.setId(id);
            servicioHotel.actualizarHotelByManagerId(hotelDTO, managerId);
            redirectAttributes.addFlashAttribute("message", "Hotel (ID: " + id + ") actualizado!");
            return "redirect:/manager/hotels";

        } catch (HotelYaExisteException e) {
            result.rejectValue("nombre", "hotel.exists", e.getMessage());
            return "hotelmanager/hotels-edit";
        } catch (EntityNotFoundException e) {
            result.rejectValue("id", "hotel.notfound", e.getMessage());
            return "hotelmanager/hotels-edit";
        }
    }

    @PostMapping("/hotels/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        Long managerId = getIdManagerActual();
        servicioHotel.borrarHotelByIdAndManagerId(id, managerId);
        return "redirect:/manager/hotels";
    }

    @GetMapping("/bookings")
    public String listBookings(Model model, RedirectAttributes redirectAttributes) {
        try {
            Long managerId = getIdManagerActual();
            List<ReservaDTO> reservaDTOs = servicioReserva.buscarReservasByManagerId(managerId);
            model.addAttribute("reservas", reservaDTOs);

            return "hotelmanager/bookings";
        } catch (EntityNotFoundException e) {
            log.error("No se encontraron reservas para el ID de gerente proporcionado.", e);
            redirectAttributes.addFlashAttribute("errorMessage", "No se encontraron reservas. Por favor, inténtalo de nuevo más tarde.");
            return "redirect:/manager/dashboard";
        } catch (Exception e) {
            log.error("Ha ocurrido un error al listar las reservas", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Se produjo un error inesperado. Por favor, inténtalo de nuevo más tarde.");
            return "redirect:/manager/dashboard";
        }
    }

    @GetMapping("/bookings/{id}")
    public String verDetallesReserva(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Long managerId = getIdManagerActual();
            ReservaDTO reservaDTO = servicioReserva.buscarReservaByIdAndManagerId(id, managerId);
            model.addAttribute("reservaDTO", reservaDTO);

            LocalDate fechaCheckIn = reservaDTO.getFechaCheckIn();
            LocalDate fechaCheckOut = reservaDTO.getFechaCheckOut();
            long duracionDays = ChronoUnit.DAYS.between(fechaCheckIn, fechaCheckOut);
            model.addAttribute("days", duracionDays);

            return "hotelmanager/bookings-details";
        } catch (EntityNotFoundException e) {
            log.error("No se encontró ninguna reserva con el ID proporcionado.", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Reserva no encontrada. Por favor, inténtalo de nuevo más tarde.");
            return "redirect:/manager/dashboard";
        } catch (Exception e) {
            log.error("Se produjo un error al mostrar los detalles de la reserva.", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Se ha producido un error inesperado. Por favor, inténtalo de nuevo más tarde.");
            return "redirect:/manager/dashboard";
        }
    }

    private Long getIdManagerActual() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return servicioUsuario.buscarUsuarioByUsername(username).getHotelManager().getId();
    }
}
