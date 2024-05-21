package es.deusto.bilboHotels.controller;

import es.deusto.bilboHotels.model.dto.ReservaDTO;
import es.deusto.bilboHotels.service.ServicioReserva;
import es.deusto.bilboHotels.service.ServicioUsuario;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final ServicioUsuario servicioUsuario;
    private final ServicioReserva servicioReserva;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "customer/dashboard";
    }

    @GetMapping("/bookings")
    public String listBookings(Model model, RedirectAttributes redirectAttributes) {
        try {
            Long clienteId = getCurrentCustomerId();
            List<ReservaDTO> reservaDTOs = servicioReserva.buscarReservasByClienteId(clienteId);
            model.addAttribute("reservas", reservaDTOs);
            return "customer/bookings";
        } catch (EntityNotFoundException e) {
            log.error("No hay usuario para el ID proporcionado", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Cliente no encontrado, porfavor logueate de nuevo.");
            return "redirect:/login";
        } catch (Exception e) {
            log.error("Ha ocurrido un error al listar las reservas", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Ha ocurrido un error inesperado, porfavor intentelo de nuevo.");
            return "redirect:/";
        }
    }

    @GetMapping("/bookings/{id}")
    public String viewBookingDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Long IdCliente = getCurrentCustomerId();
            ReservaDTO reservaDTO = servicioReserva.BuscarReservasByIdAndClienteId(id, IdCliente);
            model.addAttribute("reservaDTO", reservaDTO);

            LocalDate fechaCheckIn = reservaDTO.getFechaCheckIn();
            LocalDate fechaCheckOut = reservaDTO.getFechaCheckOut();
            long duracionDias = ChronoUnit.DAYS.between(fechaCheckIn, fechaCheckOut);
            model.addAttribute("dias", duracionDias);

            return "customer/bookings-details";
        } catch (EntityNotFoundException e) {
            log.error("No hay reservas para el id proporcionado", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Reserva no encontrada, porfavor intentelo de nuevo mas tarde.");
            return "redirect:/customer/bookings";
        } catch (Exception e) {
            log.error("Ha ocurrido un error al sacar los detalles de la reserva", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Ha ocurrido un error inesperado, porfavor intentelo de nuevo mas tarde");
            return "redirect:/";
        }
    }

    private Long getCurrentCustomerId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return servicioUsuario.buscarUsuarioByUsername(username).getCliente().getId();
    }

}