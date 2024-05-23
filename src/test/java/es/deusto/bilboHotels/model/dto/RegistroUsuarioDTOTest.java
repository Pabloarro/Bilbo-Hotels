package es.deusto.bilboHotels.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class RegistroUsuarioDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testRegistroUsuarioDTOValid() {
        RegistroUsuarioDTO registroUsuarioDTO = RegistroUsuarioDTO.builder()
                .username("usuario@example.com")
                .password("password")
                .nombre("Jon")
                .apellido("Garcia")
                //.tipoRol(TipoRol.CLIENTE)
                .build();

        Set<ConstraintViolation<RegistroUsuarioDTO>> violations = validator.validate(registroUsuarioDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testRegistroUsuarioDTOInvalidEmail() {
        RegistroUsuarioDTO registroUsuarioDTO = RegistroUsuarioDTO.builder()
                .username("usuarioexample.com")
                .password("password")
                .nombre("Jon")
                .apellido("Garcia")
                //.tipoRol(TipoRol.CLIENTE)
                .build();

        Set<ConstraintViolation<RegistroUsuarioDTO>> violations = validator.validate(registroUsuarioDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<RegistroUsuarioDTO> violation = violations.iterator().next();
        assertEquals("Email invalido", violation.getMessage());
    }

    @Test
    public void testRegistroUsuarioDTOInvalidPassword() {
        RegistroUsuarioDTO registroUsuarioDTO = RegistroUsuarioDTO.builder()
                .username("usuario@example.com")
                .password("pwd")
                .nombre("Jon")
                .apellido("Garcia")
                //.tipoRol(TipoRol.CLIENTE)
                .build();

        Set<ConstraintViolation<RegistroUsuarioDTO>> violations = validator.validate(registroUsuarioDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<RegistroUsuarioDTO> violation = violations.iterator().next();
        assertEquals("La contrasenia debe tener entre 6 y 20 caracteres.", violation.getMessage());
    }
}
