package es.deusto.bilboHotels.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResetearPasswordDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidResetearPasswordDTO() {
        ResetearPasswordDTO resetearPasswordDTO = ResetearPasswordDTO.builder()
                .antiguaPassword("oldPassword")
                .nuevaPassword("newPassword")
                .confirmarNuevaPassword("newPassword")
                .build();

        Set<ConstraintViolation<ResetearPasswordDTO>> violations = validator.validate(resetearPasswordDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidAntiguaPassword() {
        ResetearPasswordDTO resetearPasswordDTO = ResetearPasswordDTO.builder()
                .antiguaPassword("") // Contraseña antigua vacía
                .nuevaPassword("newPassword")
                .confirmarNuevaPassword("newPassword")
                .build();

        Set<ConstraintViolation<ResetearPasswordDTO>> violations = validator.validate(resetearPasswordDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidNuevaPassword() {
        ResetearPasswordDTO resetearPasswordDTO = ResetearPasswordDTO.builder()
                .antiguaPassword("oldPassword")
                .nuevaPassword("") // Contraseña nueva vacía
                .confirmarNuevaPassword("newPassword")
                .build();

        Set<ConstraintViolation<ResetearPasswordDTO>> violations = validator.validate(resetearPasswordDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidConfirmarNuevaPassword() {
        ResetearPasswordDTO resetearPasswordDTO = ResetearPasswordDTO.builder()
                .antiguaPassword("oldPassword")
                .nuevaPassword("newPassword")
                .confirmarNuevaPassword("") // Confirmación de contraseña nueva vacía
                .build();

        Set<ConstraintViolation<ResetearPasswordDTO>> violations = validator.validate(resetearPasswordDTO);

        assertFalse(violations.isEmpty());
    }
}

