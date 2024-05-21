package es.deusto.bilboHotels.controller;

import es.deusto.bilboHotels.exception.HotelYaExisteException;
import es.deusto.bilboHotels.exception.NombreUsuarioYaExisteException;
import es.deusto.bilboHotels.model.dto.ReservaDTO;
import es.deusto.bilboHotels.model.dto.HotelDTO;
import es.deusto.bilboHotels.model.dto.UsuarioDTO;
import es.deusto.bilboHotels.service.ServicioReserva;
import es.deusto.bilboHotels.service.ServicioHotel;
import es.deusto.bilboHotels.service.ServicioUsuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final ServicioUsuario servicioUsuario;
    private final ServicioHotel servicioHotel;
    private final ServicioReserva servicioReserva;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsuarios(Model model) {
        List<UsuarioDTO> usuarioDTOList = servicioUsuario.buscarAllUsuarios();
        model.addAttribute("usuarios", usuarioDTOList);
        return "admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String mostrarFormularioDeEdiciónDeUsuario(@PathVariable Long id, Model model) {
        UsuarioDTO usuarioDTO = servicioUsuario.buscarUsuarioById(id);
        model.addAttribute("usuario", usuarioDTO);
        return "admin/users-edit";
    }

    @PostMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, @Valid @ModelAttribute("usuario") UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/users-edit";
        }
        try {
            servicioUsuario.actualizarUsuario(usuarioDTO);
        } catch (NombreUsuarioYaExisteException e) {
            result.rejectValue("username", "usuario.exists", "Usuario ya registrado!");
            return "admin/users-edit";
        }

        redirectAttributes.addFlashAttribute("actualizadoUsuarioId", usuarioDTO.getId());
        return "redirect:/admin/users?success";
    }

    
    @PostMapping("/users/delete/{id}")
    public String borrarUsuario(@PathVariable Long id) {
        servicioUsuario.borrarUsuarioById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/hotels")
    public String listHoteles(Model model) {
        List<HotelDTO> hotelDTOList = servicioHotel.buscarAllHoteles();
        model.addAttribute("hotels", hotelDTOList);
        return "admin/hotels";
    }

    @GetMapping("/hotels/edit/{id}")
    public String mostrarFormularioDeEdiciónDeHotel(@PathVariable Long id, Model model) {
        HotelDTO hotelDTO = servicioHotel.buscarHotelDtoById(id);
        model.addAttribute("hotel", hotelDTO);
        return "admin/hotels-edit";
    }

    @PostMapping("/hotels/edit/{id}")
    public String editarHotel(@PathVariable Long id, @Valid @ModelAttribute("hotel") HotelDTO hotelDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/hotels-edit";
        }
        try {
            servicioHotel.actualizarHotel(hotelDTO);
        } catch (HotelYaExisteException e) {
            result.rejectValue("nombre", "hotel.exists", e.getMessage());
            return "admin/hotels-edit";
        }

        redirectAttributes.addFlashAttribute("updatedHotelId", hotelDTO.getId());
        return "redirect:/admin/hotels?success";
    }

    @PostMapping("/hotels/delete/{id}")
    public String borrarHotel(@PathVariable Long id) {
        servicioHotel.borrarHotelById(id);
        return "redirect:/admin/hotels";
    }

    @GetMapping("/bookings")
    public String listReservas(Model model) {
        List<ReservaDTO> reservaDTOList = servicioReserva.buscarAllReservas();
        model.addAttribute("reservas", reservaDTOList);
        return "admin/bookings";
    }

    @GetMapping("/bookings/{id}")
    public String verDetallesReserva(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ReservaDTO reservaDTO = servicioReserva.buscarReservaById(id);
            model.addAttribute("reservaDTO", reservaDTO);

            LocalDate fechaCheckIn = reservaDTO.getFechaCheckIn();
            LocalDate fechaCheckOut = reservaDTO.getFechaCheckOut();
            long duracionDias = ChronoUnit.DAYS.between(fechaCheckIn, fechaCheckOut);
            model.addAttribute("dias", duracionDias);

            return "admin/bookings-details";
        } catch (EntityNotFoundException e) {
            log.error("Reserva no encontrada para el ID proporcionado", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Reserva no encontrada. Porfavor intentelo mas tarde.");
            return "redirect:/admin/dashboard";
        } catch (Exception e) {
            log.error("Ha ocurrido un error al desplegar los detalles de la reserva", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error inesperado. Porfavor intentelo mas tarde.");
            return "redirect:/admin/dashboard";
        }
    }

}
