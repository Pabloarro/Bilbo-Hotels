package es.deusto.bilboHotels.model.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DireccionDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testDireccionDTOValid() {
        DireccionDTO direccion = DireccionDTO.builder()
                .id(1L)
                .lineaDireccion("Calle Ejemplo 123")
                .ciudad("Bilbao")
                .pais("España")
                .build();

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccion);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testDireccionDTOInvalidLineaDireccion() {
        DireccionDTO direccion = DireccionDTO.builder()
                .id(1L)
                .lineaDireccion("!@#")
                .ciudad("Bilbao")
                .pais("España")
                .build();

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccion);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<DireccionDTO> violation = violations.iterator().next();
        assertEquals("La línea de dirección solo puede contener letras, números y algunos caracteres especiales (., :, -).", violation.getMessage());
    }

    @Test
    public void testDireccionDTOBlankLineaDireccion() {
        DireccionDTO direccion = DireccionDTO.builder()
                .id(1L)
                .lineaDireccion("")
                .ciudad("Bilbao")
                .pais("España")
                .build();

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccion);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<DireccionDTO> violation = violations.iterator().next();
        assertEquals("La línea de dirección no puede estar vacía.", violation.getMessage());
    }

    @Test
    public void testDireccionDTOInvalidCiudad() {
        DireccionDTO direccion = DireccionDTO.builder()
                .id(1L)
                .lineaDireccion("Calle Ejemplo 123")
                .ciudad("12345")
                .pais("España")
                .build();

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccion);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<DireccionDTO> violation = violations.iterator().next();
        assertEquals("La ciudad solo puede contener letras", violation.getMessage());
    }

    @Test
    public void testDireccionDTOBlankCiudad() {
        DireccionDTO direccion = DireccionDTO.builder()
                .id(1L)
                .lineaDireccion("Calle Ejemplo 123")
                .ciudad("")
                .pais("España")
                .build();

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccion);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<DireccionDTO> violation = violations.iterator().next();
        assertEquals("La ciudad no puede estar vacia", violation.getMessage());
    }

    @Test
    public void testDireccionDTOInvalidPais() {
        DireccionDTO direccion = DireccionDTO.builder()
                .id(1L)
                .lineaDireccion("Calle Ejemplo 123")
                .ciudad("Bilbao")
                .pais("12345")
                .build();

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccion);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<DireccionDTO> violation = violations.iterator().next();
        assertEquals("El país solo puede contener letras", violation.getMessage());
    }

    @Test
    public void testDireccionDTOBlankPais() {
        DireccionDTO direccion = DireccionDTO.builder()
                .id(1L)
                .lineaDireccion("Calle Ejemplo 123")
                .ciudad("Bilbao")
                .pais("")
                .build();

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccion);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<DireccionDTO> violation = violations.iterator().next();
        assertEquals("El país no puede estar vacío", violation.getMessage());
    }
}