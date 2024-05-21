package es.deusto.bilboHotels.controller;

import es.deusto.bilboHotels.exception.NombreUsuarioYaExisteException;
import es.deusto.bilboHotels.model.dto.UsuarioDTO;
import es.deusto.bilboHotels.service.ServicioUsuario;
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

@Controller
@RequiredArgsConstructor
@Slf4j
public class MiCuentaController {

    private final ServicioUsuario servicioUsuario;

    // Customer actions
    @GetMapping("/customer/account")
    public String showCustomerAccount(Model model){
        log.debug("Mostrando la cuenta del cliente");
        añadirDatosDeUsuarioConectadoToModel(model);
        return "customer/account";
    }

    @GetMapping("/customer/account/edit")
    public String showCustomerEditForm(Model model){
        log.debug("Mostrando el formulario de edición de la cuenta del cliente");
        añadirDatosDeUsuarioConectadoToModel(model);
        return "customer/account-edit";
    }

    @PostMapping("/customer/account/edit")
    public String editarCuentaCliente(@Valid @ModelAttribute("usuario") UsuarioDTO usuarioDTO, BindingResult result) {
        log.info("Intentando editar los detalles de la cuenta del cliente con ID: {}", usuarioDTO.getId());
        if (result.hasErrors()) {
            log.warn("Se produjeron errores de validación al editar la cuenta del cliente.");
            return "customer/account-edit";
        }
        try {
            servicioUsuario.actualizarUsuarioLoggeado(usuarioDTO);
            log.info("La cuenta del cliente se ha editado correctamente.");
        } catch (NombreUsuarioYaExisteException e) {
            log.error("Error: El nombre de usuario ya existe.", e);
            result.rejectValue("username", "user.exists", "Nombre de usuario ya registrado!");
            return "customer/account-edit";
        }
        return "redirect:/customer/account?success";
    }

    // Hotel Manager actions
    @GetMapping("/manager/account")
    public String mostrarCuentaManagerHotel(Model model){
        log.debug("Mostrando la cuenta del manager del hotel");
        añadirDatosDeUsuarioConectadoToModel(model);
        return "hotelmanager/account";
    }

    @GetMapping("/manager/account/edit")
    public String MostrarFormularioEdicionManagerhotel(Model model){
        log.debug("Mostrando el formulario de edición de la cuenta del manager del hotel");
        añadirDatosDeUsuarioConectadoToModel(model);
        return "hotelmanager/account-edit";
    }

    @PostMapping("/manager/account/edit")
    public String editarCuentaHotelManager(@Valid @ModelAttribute("usuario") UsuarioDTO usuarioDTO, BindingResult result) {
        log.info("Intentando editar los detalles de la cuenta del manager del hotel para el ID: {}", usuarioDTO.getId());
        if (result.hasErrors()) {
            log.warn("Se produjeron errores de validación al editar la cuenta del gerente del hotel.");
            return "hotelmanager/account-edit";
        }
        try {
            servicioUsuario.actualizarUsuarioLoggeado(usuarioDTO);
            log.info("Se ha editado exitosamente la cuenta del gerente del hotel.");
        } catch (NombreUsuarioYaExisteException e) {
            log.error("Error: El nombre de usuario ya existe.", e);
            result.rejectValue("username", "usuario.exists", "Nombre usuario ya registrado!");
            return "hotelmanager/account-edit";
        }
        return "redirect:/manager/account?success";
    }

    private void añadirDatosDeUsuarioConectadoToModel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UsuarioDTO usuarioDTO = servicioUsuario.buscarUsuarioDTOByUsername(username);
        log.info("Agregando los datos del usuario conectado al modelo para el ID de usuario: {}", usuarioDTO.getId());
        model.addAttribute("usuario", usuarioDTO);
    }

}
