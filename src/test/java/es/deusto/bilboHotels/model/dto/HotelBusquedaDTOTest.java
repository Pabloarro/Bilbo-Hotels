package es.deusto.bilboHotels.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void testEqualsAndHashCode() {
        HotelBusquedaDTO dto1 = HotelBusquedaDTO.builder()
                .ciudad("Bilbao")
                .fechaCheckIn(LocalDate.now())
                .fechaCheckOut(LocalDate.now().plusDays(1))
                .build();

        HotelBusquedaDTO dto2 = HotelBusquedaDTO.builder()
                .ciudad("Bilbao")
                .fechaCheckIn(LocalDate.now())
                .fechaCheckOut(LocalDate.now().plusDays(1))
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Cambiar un campo debería cambiar el hashcode
        dto2.setCiudad("Madrid");
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testToString() {
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(1);
        HotelBusquedaDTO dto = HotelBusquedaDTO.builder()
                .ciudad("Bilbao")
                .fechaCheckIn(checkIn)
                .fechaCheckOut(checkOut)
                .build();

        String expectedToString = "HotelBusquedaDTO(ciudad=Bilbao, fechaCheckIn=" + checkIn.toString() + ", fechaCheckOut=" + checkOut.toString() + ")";
        assertEquals(expectedToString, dto.toString());
    }

    @Test
    public void testGettersAndSetters() {
        HotelBusquedaDTO dto = HotelBusquedaDTO.builder().build();
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(1);

        dto.setCiudad("Bilbao");
        dto.setFechaCheckIn(checkIn);
        dto.setFechaCheckOut(checkOut);

        assertEquals("Bilbao", dto.getCiudad());
        assertEquals(checkIn, dto.getFechaCheckIn());
        assertEquals(checkOut, dto.getFechaCheckOut());
    }
}