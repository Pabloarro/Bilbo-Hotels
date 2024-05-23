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

public class HotelRegistroDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testHotelRegistroDTOValid() {
        HotelRegistroDTO hotelRegistroDTO = HotelRegistroDTO.builder()
                .nombre("Hotel Ejemplo")
                .direccionDTO(DireccionDTO.builder().build())
                .habitacionDTOs(new ArrayList<>())
                .build();

        Set<ConstraintViolation<HotelRegistroDTO>> violations = validator.validate(hotelRegistroDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testHotelRegistroDTOInvalidNombre() {
        HotelRegistroDTO hotelRegistroDTO = HotelRegistroDTO.builder()
                .nombre("1234")
                .direccionDTO(DireccionDTO.builder().build())
                .habitacionDTOs(new ArrayList<>())
                .build();

        Set<ConstraintViolation<HotelRegistroDTO>> violations = validator.validate(hotelRegistroDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HotelRegistroDTO> violation = violations.iterator().next();
        assertEquals("El nombre del hotel solo puede contener letras y numoers", violation.getMessage());
    }

    @Test
    public void testHotelRegistroDTOInvalidDireccion() {
        HotelRegistroDTO hotelRegistroDTO = HotelRegistroDTO.builder()
                .nombre("Hotel Ejemplo")
                .direccionDTO(null)
                .habitacionDTOs(new ArrayList<>())
                .build();

        Set<ConstraintViolation<HotelRegistroDTO>> violations = validator.validate(hotelRegistroDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HotelRegistroDTO> violation = violations.iterator().next();
        assertEquals("direccionDTO no puede ser nulo", violation.getMessage());
    }
}
