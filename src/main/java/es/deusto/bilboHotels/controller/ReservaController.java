package es.deusto.bilboHotels.controller;

import es.deusto.bilboHotels.model.dto.*;
import es.deusto.bilboHotels.service.ServicioReserva;
import es.deusto.bilboHotels.service.ServicioHotel;
import es.deusto.bilboHotels.service.ServicioUsuario;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class ReservaController {

    private final ServicioHotel servicioHotel;
    private final ServicioUsuario servicioUsuario;
    private final ServicioReserva servicioReserva;

    @PostMapping("/initiate")
    public String initiateBooking(@ModelAttribute InicioReservaDTO inicioReservaDTO, HttpSession session) {
        session.setAttribute("inicioReservaDTO", inicioReservaDTO);
        log.debug("Se estableció inicioReservaDTO en la sesión: {}", inicioReservaDTO);
        return "redirect:/booking/payment";
    }

    @GetMapping("/payment")
    public String mostrarPaginaPago(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        InicioReservaDTO inicioReservaDTO = (InicioReservaDTO) session.getAttribute("inicioReservaDTO");
        log.debug("Se ha recuperado iniciioReservaDTO de la sesión: {}", inicioReservaDTO);

        if (inicioReservaDTO == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tu sesión ha expirado. Por favor, inicia una nueva búsqueda.");
            return "redirect:/search";
        }

        HotelDTO hotelDTO = servicioHotel.buscarHotelDtoById(inicioReservaDTO.getHotelId());

        model.addAttribute("inicioReservaDTO", inicioReservaDTO);
        model.addAttribute("hotelDTO", hotelDTO);
        model.addAttribute("tarjetaPagoDTO", new TarjetaPagoDTO());

        return "booking/payment";
    }

    @PostMapping("/payment")
    public String confirmBooking(@Valid @ModelAttribute("tarjetaPagoDTO") TarjetaPagoDTO tarjetaPagoDTO, BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        InicioReservaDTO inicioReservaDTO = (InicioReservaDTO) session.getAttribute("inicioReservaDTO");
        log.debug("Se ha recuperado inicioReservaDTO de la sesión al comienzo de complete reserva. {}", inicioReservaDTO);
        if (inicioReservaDTO == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tu sesión ha expirado. Por favor, inicia una nueva búsqueda.");
            return "redirect:/search";
        }

        if (result.hasErrors()) {
            log.warn("Se produjeron errores de validación al completar la reserva.");
            HotelDTO hotelDTO = servicioHotel.buscarHotelDtoById(inicioReservaDTO.getHotelId());
            model.addAttribute("inicioReservaDTO", inicioReservaDTO);
            model.addAttribute("hotelDTO", hotelDTO);
            model.addAttribute("tarjetaPagoDTO", tarjetaPagoDTO);
            return "booking/payment";
        }

        try {
            Long userId = getUsuarioIdLogueado();
            ReservaDTO reservaDTO = servicioReserva.confirmarReserva(inicioReservaDTO, userId);
            redirectAttributes.addFlashAttribute("reservaDTO", reservaDTO);

            return "redirect:/booking/confirmation";
        } catch (Exception e) {
            log.error("Ocurrió un error al completar la reserva.", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Se produjo un error inesperado. Por favor, inténtalo de nuevo más tarde.");
            return "redirect:/booking/payment";
        }
    }

    @GetMapping("/confirmation")
    public String mostrarPaginaConfirmacion(Model model, RedirectAttributes redirectAttributes) {
        // Attempt to retrieve the bookingDTO from flash attributes
        ReservaDTO reservaDTO = (ReservaDTO) model.asMap().get("reservaDTO");

        if (reservaDTO == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tu sesión ha expirado o el proceso de reserva no se completó correctamente. Por favor, inicia una nueva búsqueda");
            return "redirect:/search";
        }

        LocalDate fechaCheckIn = reservaDTO.getFechaCheckIn();
        LocalDate fechaCheckOut = reservaDTO.getFechaCheckOut();
        long duracionDias = ChronoUnit.DAYS.between(fechaCheckIn, fechaCheckOut);

        model.addAttribute("dias", duracionDias);
        model.addAttribute("reservaDTO", reservaDTO);

        return "booking/confirmation";
    }

    
    private Long getUsuarioIdLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UsuarioDTO usuarioDTO = servicioUsuario.buscarUsuarioDTOByUsername(username);
        log.info("Se recuperó el ID del usuario conectado: {}", usuarioDTO.getId());
        return usuarioDTO.getId();
    }

}