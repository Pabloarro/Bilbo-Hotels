package es.deusto.bilboHotels.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.deusto.bilboHotels.model.enums.TipoHabitacion;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class HabitacionSeleccionDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testHabitacionSeleccionDTOValid() {
        HabitacionSeleccionDTO habitacionSeleccionDTO = HabitacionSeleccionDTO.builder()
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contar(2)
                .build();

        Set<ConstraintViolation<HabitacionSeleccionDTO>> violations = validator.validate(habitacionSeleccionDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testHabitacionSeleccionDTOInvalidContarZero() {
        HabitacionSeleccionDTO habitacionSeleccionDTO = HabitacionSeleccionDTO.builder()
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contar(0)
                .build();

        Set<ConstraintViolation<HabitacionSeleccionDTO>> violations = validator.validate(habitacionSeleccionDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HabitacionSeleccionDTO> violation = violations.iterator().next();
        assertEquals("contar debe ser mayor que 0", violation.getMessage());
    }

    @Test
    public void testHabitacionSeleccionDTOInvalidTipoHabitacion() {
        HabitacionSeleccionDTO habitacionSeleccionDTO = HabitacionSeleccionDTO.builder()
                .tipoHabitacion(null)
                .contar(2)
                .build();

        Set<ConstraintViolation<HabitacionSeleccionDTO>> violations = validator.validate(habitacionSeleccionDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HabitacionSeleccionDTO> violation = violations.iterator().next();
        assertEquals("tipoHabitacion no puede ser nulo", violation.getMessage());
    }
}
