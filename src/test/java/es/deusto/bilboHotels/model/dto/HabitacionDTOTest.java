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

public class HabitacionDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testHabitacionDTOValid() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .id(1L)
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(2)
                .precioPorNoche(100.0)
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testHabitacionDTOInvalidContadorHabitacion() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .id(1L)
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(-1)
                .precioPorNoche(100.0)
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HabitacionDTO> violation = violations.iterator().next();
        assertEquals("El contador de habitaciones debe ser 0 o mas", violation.getMessage());
    }

    @Test
    public void testHabitacionDTOInvalidPrecioPorNoche() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .id(1L)
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(2)
                .precioPorNoche(-100.0)
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HabitacionDTO> violation = violations.iterator().next();
        assertEquals("El precio por noche debe ser 0 o más.", violation.getMessage());
    }

    @Test
    public void testHabitacionDTOInvalidHotelId() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .id(1L)
                .hotelId(null)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(2)
                .precioPorNoche(100.0)
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HabitacionDTO> violation = violations.iterator().next();
        assertEquals("El hotelId no puede estar vacío", violation.getMessage());
    }

    @Test
    public void testHabitacionDTOInvalidTipoHabitacion() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .id(1L)
                .hotelId(1L)
                .tipoHabitacion(null)
                .contadorHabitacion(2)
                .precioPorNoche(100.0)
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HabitacionDTO> violation = violations.iterator().next();
        assertEquals("El tipo de habitación no puede estar vacío", violation.getMessage());
    }

    @Test
    public void testHabitacionDTOInvalidPrecioPorNocheZero() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .id(1L)
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(2)
                .precioPorNoche(0.0)
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HabitacionDTO> violation = violations.iterator().next();
        assertEquals("El precio por noche debe ser 0 o más.", violation.getMessage());
    }

}
