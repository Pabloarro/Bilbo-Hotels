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

public class HotelDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testHotelDTOValid() {
        HotelDTO hotelDTO = HotelDTO.builder()
                .id(1L)
                .nombre("Hotel Ejemplo")
                .direccionDTO(DireccionDTO.builder().build())
                .habitacionDTOs(new ArrayList<>())
                .managerUsername("manager")
                .build();

        Set<ConstraintViolation<HotelDTO>> violations = validator.validate(hotelDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testHotelDTOInvalidNombre() {
        HotelDTO hotelDTO = HotelDTO.builder()
                .id(1L)
                .nombre("1234")
                .direccionDTO(DireccionDTO.builder().build())
                .habitacionDTOs(new ArrayList<>())
                .managerUsername("manager")
                .build();

        Set<ConstraintViolation<HotelDTO>> violations = validator.validate(hotelDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HotelDTO> violation = violations.iterator().next();
        assertEquals("El nombre del hotel solo puede contener letras y numeros", violation.getMessage());
    }

    @Test
    public void testHotelDTOInvalidDireccion() {
        HotelDTO hotelDTO = HotelDTO.builder()
                .id(1L)
                .nombre("Hotel Ejemplo")
                .direccionDTO(null)
                .habitacionDTOs(new ArrayList<>())
                .managerUsername("manager")
                .build();

        Set<ConstraintViolation<HotelDTO>> violations = validator.validate(hotelDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HotelDTO> violation = violations.iterator().next();
        assertEquals("direccionDTO no puede ser nulo", violation.getMessage());
    }

    @Test
    public void testHotelDTOInvalidManagerUsername() {
        HotelDTO hotelDTO = HotelDTO.builder()
                .id(1L)
                .nombre("Hotel Ejemplo")
                .direccionDTO(DireccionDTO.builder().build())
                .habitacionDTOs(new ArrayList<>())
                .managerUsername(null)
                .build();

        Set<ConstraintViolation<HotelDTO>> violations = validator.validate(hotelDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HotelDTO> violation = violations.iterator().next();
        assertEquals("managerUsername no puede ser nulo", violation.getMessage());
    }
}
