package es.deusto.bilboHotels.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.warn("Redirigir a un usuario autenticado a la página de destino designada según su rol.");

        String redirectUrl = RedirectUtil.getRedirectUrl(authentication);

        log.info("Redirect path: " + redirectUrl);

        if (redirectUrl == null) {
            throw new IllegalStateException("No se pudo determinar el rol para la redireccion");
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
