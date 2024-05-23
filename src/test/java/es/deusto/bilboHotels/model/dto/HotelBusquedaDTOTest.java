package es.deusto.bilboHotels.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class HotelBusquedaDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testHotelBusquedaDTOValid() {
        HotelBusquedaDTO hotelBusquedaDTO = HotelBusquedaDTO.builder()
                .ciudad("Bilbao")
                .fechaCheckIn(LocalDate.now())
                .fechaCheckOut(LocalDate.now().plusDays(1))
                .build();

        Set<ConstraintViolation<HotelBusquedaDTO>> violations = validator.validate(hotelBusquedaDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testHotelBusquedaDTOInvalidCiudad() {
        HotelBusquedaDTO hotelBusquedaDTO = HotelBusquedaDTO.builder()
                .ciudad("1234")
                .fechaCheckIn(LocalDate.now())
                .fechaCheckOut(LocalDate.now().plusDays(1))
                .build();

        Set<ConstraintViolation<HotelBusquedaDTO>> violations = validator.validate(hotelBusquedaDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HotelBusquedaDTO> violation = violations.iterator().next();
        assertEquals("La ciudad solo debe contener letras, apóstrofes(') o guiones(-)", violation.getMessage());
    }

    @Test
    public void testHotelBusquedaDTOInvalidFechaCheckIn() {
        LocalDate fechaPasada = LocalDate.now().minusDays(1);
        HotelBusquedaDTO hotelBusquedaDTO = HotelBusquedaDTO.builder()
                .ciudad("Bilbao")
                .fechaCheckIn(fechaPasada)
                .fechaCheckOut(LocalDate.now().plusDays(1))
                .build();

        Set<ConstraintViolation<HotelBusquedaDTO>> violations = validator.validate(hotelBusquedaDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<HotelBusquedaDTO> violation = violations.iterator().next();
        assertEquals("La Fecha de Check-in no puede estar en pasado", violation.getMessage());
    }
}
