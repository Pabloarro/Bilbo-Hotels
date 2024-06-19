package es.deusto.bilboHotels.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    @Test
    public void testEqualsAndHashCode() {
        ResetearPasswordDTO dto1 = ResetearPasswordDTO.builder()
            .antiguaPassword("password1")
            .nuevaPassword("newPassword")
            .confirmarNuevaPassword("newPassword")
            .build();

        ResetearPasswordDTO dto2 = ResetearPasswordDTO.builder()
            .antiguaPassword("password1")
            .nuevaPassword("newPassword")
            .confirmarNuevaPassword("newPassword")
            .build();

        ResetearPasswordDTO dto3 = ResetearPasswordDTO.builder()
            .antiguaPassword("password2")
            .nuevaPassword("newPassword")
            .confirmarNuevaPassword("newPassword")
            .build();

        System.out.println("dto1: " + dto1);
        System.out.println("dto2: " + dto2);
        System.out.println("dto3: " + dto3);

        assertTrue(dto1.equals(dto2));
        assertTrue(dto2.equals(dto1));
        assertTrue(dto1.equals(dto1));
        assertTrue(dto2.equals(dto2));
        assertTrue(dto3.equals(dto3));

        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());

        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("String"));
    }

    @Test
    public void testToString() {
        ResetearPasswordDTO resetearPasswordDTO = ResetearPasswordDTO.builder()
            .antiguaPassword("oldPassword")
            .nuevaPassword("newPassword")
            .confirmarNuevaPassword("newPassword")
            .build();

        String expectedToString = "ResetearPasswordDTO(antiguaPassword=oldPassword, nuevaPassword=newPassword, confirmarNuevaPassword=newPassword)";
        assertEquals(expectedToString, resetearPasswordDTO.toString());
    }

    @Test
    public void testSettersAndGetters() {
        ResetearPasswordDTO resetearPasswordDTO = ResetearPasswordDTO.builder()
                .antiguaPassword("oldPassword")
                .nuevaPassword("newPassword")
                .confirmarNuevaPassword("newPassword")
                .build();
    
        assertEquals("oldPassword", resetearPasswordDTO.getAntiguaPassword());
        assertEquals("newPassword", resetearPasswordDTO.getNuevaPassword());
        assertEquals("newPassword", resetearPasswordDTO.getConfirmarNuevaPassword());

        resetearPasswordDTO.setAntiguaPassword("updatedPassword");
        resetearPasswordDTO.setNuevaPassword("newPassword2");
        resetearPasswordDTO.setConfirmarNuevaPassword("newPassword2");

        assertEquals("updatedPassword", resetearPasswordDTO.getAntiguaPassword());
        assertEquals("newPassword2", resetearPasswordDTO.getNuevaPassword());
        assertEquals("newPassword2", resetearPasswordDTO.getConfirmarNuevaPassword());
    }
  
}

