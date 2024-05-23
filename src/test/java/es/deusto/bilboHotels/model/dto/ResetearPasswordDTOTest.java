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

public class ResetearPasswordDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testResetearPasswordDTOValid() {
        ResetearPasswordDTO resetearPasswordDTO = ResetearPasswordDTO.builder()
                .antiguaPassword("oldPassword")
                .nuevaPassword("newPassword")
                .confirmarNuevaPassword("newPassword")
                .build();

        Set<ConstraintViolation<ResetearPasswordDTO>> violations = validator.validate(resetearPasswordDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testResetearPasswordDTOInvalidMismatchedPasswords() {
        ResetearPasswordDTO resetearPasswordDTO = ResetearPasswordDTO.builder()
                .antiguaPassword("oldPassword")
                .nuevaPassword("newPassword")
                .confirmarNuevaPassword("wrongPassword")
                .build();

        Set<ConstraintViolation<ResetearPasswordDTO>> violations = validator.validate(resetearPasswordDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<ResetearPasswordDTO> violation = violations.iterator().next();
        assertEquals("La contrasenia no puede estar vacía.", violation.getMessage());
    }
}
