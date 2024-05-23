package es.deusto.bilboHotels.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class HotelDisponibilidadDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testHotelDisponibilidadDTOValid() {
        HotelDisponibilidadDTO hotelDisponibilidadDTO = HotelDisponibilidadDTO.builder()
                .id(1L)
                .nombre("Hotel Ejemplo")
                .direccionDTO(DireccionDTO.builder().build())
                .habitacionDTOs(new ArrayList<>())
                .maxHabitacionesIndividualDisponibles(10)
                .maxHabitacionesDoblesDisponibles(5)
                .build();

        Set<ConstraintViolation<HotelDisponibilidadDTO>> violations = validator.validate(hotelDisponibilidadDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testHotelDisponibilidadDTOInvalidNombreNull() {
        HotelDisponibilidadDTO hotelDisponibilidadDTO = HotelDisponibilidadDTO.builder()
                .id(1L)
                .nombre(null)
                .direccionDTO(DireccionDTO.builder().build())
                .habitacionDTOs(new ArrayList<>())
                .maxHabitacionesIndividualDisponibles(10)
                .maxHabitacionesDoblesDisponibles(5)
                .build();

        Set<ConstraintViolation<HotelDisponibilidadDTO>> violations = validator.validate(hotelDisponibilidadDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HotelDisponibilidadDTO> violation = violations.iterator().next();
        assertEquals("nombre no puede ser nulo", violation.getMessage());
    }

    @Test
    public void testHotelDisponibilidadDTOInvalidDireccionNull() {
        HotelDisponibilidadDTO hotelDisponibilidadDTO = HotelDisponibilidadDTO.builder()
                .id(1L)
                .nombre("Hotel Ejemplo")
                .direccionDTO(null)
                .habitacionDTOs(new ArrayList<>())
                .maxHabitacionesIndividualDisponibles(10)
                .maxHabitacionesDoblesDisponibles(5)
                .build();

        Set<ConstraintViolation<HotelDisponibilidadDTO>> violations = validator.validate(hotelDisponibilidadDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HotelDisponibilidadDTO> violation = violations.iterator().next();
        assertEquals("direccionDTO no puede ser nulo", violation.getMessage());
    }
}
