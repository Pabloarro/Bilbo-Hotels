package es.deusto.bilboHotels.controller;

import es.deusto.bilboHotels.exception.NombreUsuarioYaExisteException;
import es.deusto.bilboHotels.model.enums.TipoRol;
import es.deusto.bilboHotels.model.dto.RegistroUsuarioDTO;
import es.deusto.bilboHotels.security.RedirectUtil;
import es.deusto.bilboHotels.service.ServicioUsuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AutenticacionController {

    private final ServicioUsuario servicioUsuario;

    @GetMapping("/")
    public String paginaInicio(Authentication authentication) {
        String redirect = getAuthenticatedUserRedirectUrl(authentication);
        if (redirect != null) return redirect;
        log.debug("Accediendo a la pagina de inicio");
        return "index";
    }

    @GetMapping("/login")
    public String paginaLogin(Authentication authentication) {
        String redirect = getAuthenticatedUserRedirectUrl(authentication);
        if (redirect != null) return redirect;
        log.debug("Accediendo a la pagina de login");
        return "login";
    }

    @GetMapping("/register/customer")
    public String showCustomerRegistrationForm(@ModelAttribute("usuario") RegistroUsuarioDTO registroDTO, Authentication authentication) {
        String redirect = getAuthenticatedUserRedirectUrl(authentication);
        if (redirect != null) return redirect;
        log.info("Mostrando formulario de registro de cliente");
        return "register-customer";
    }

    @PostMapping("/register/customer")
    public String registerCustomerAccount(@Valid @ModelAttribute("usuario") RegistroUsuarioDTO registroDTO, BindingResult result) {
        log.info("Intentando registrar la cuenta del cliente.: {}", registroDTO.getUsername());
        registroDTO.setTipoRol(TipoRol.CLIENTE);
        return registrarUsuario(registroDTO, result, "register-customer", "register/customer");
    }

    @GetMapping("/register/manager")
    public String showManagerRegistrationForm(@ModelAttribute("usuario") RegistroUsuarioDTO registroDTO, Authentication authentication) {
        String redirect = getAuthenticatedUserRedirectUrl(authentication);
        if (redirect != null) return redirect;
        log.info("Mostrando formulario de registro para manager");
        return "register-manager";
    }

    @PostMapping("/register/manager")
    public String registerManagerAccount(@Valid @ModelAttribute("usuario") RegistroUsuarioDTO registroDTO, BindingResult result) {
        log.info("Intentanto registrar la cuenta del manager: {}", registroDTO.getUsername());
        registroDTO.setTipoRol(TipoRol.HOTEL_MANAGER);
        return registrarUsuario(registroDTO, result, "register-manager", "register/manager");
    }

    private String registrarUsuario(RegistroUsuarioDTO registroDTO, BindingResult result, String view, String redirectUrl) {
        if (result.hasErrors()) {
            log.warn("El registro fallo debido a errores de validacion: {}", result.getAllErrors());
            return view;
        }
        try {
            servicioUsuario.guardarUsuario(registroDTO);
            log.info("Registro de usuario exitoso: {}", registroDTO.getUsername());
        } catch (NombreUsuarioYaExisteException e) {
            log.error("El registro ha fallado porque el nombre de usuario ya existe: {}", e.getMessage());
            result.rejectValue("username", "user.exists", e.getMessage());
            return view;
        }
        return "redirect:/" + redirectUrl + "?success";
    }

    private String getAuthenticatedUserRedirectUrl(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String redirectUrl = RedirectUtil.getRedirectUrl(authentication);
            if (redirectUrl != null) {
                return "redirect:" + redirectUrl;
            }
        }
        return null;
    }

}

