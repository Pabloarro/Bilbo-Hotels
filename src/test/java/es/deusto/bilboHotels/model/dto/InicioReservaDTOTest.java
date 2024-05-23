package es.deusto.bilboHotels.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class InicioReservaDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testInicioReservaDTOValid() {
        InicioReservaDTO inicioReservaDTO = InicioReservaDTO.builder()
                .hotelId(1L)
                .fechaCheckIn(LocalDate.now())
                .fechaCheckOut(LocalDate.now().plusDays(1))
                .duracionDias(1L)
                .habitacionSelecciones(new ArrayList<>())
                .precioTotal(BigDecimal.TEN)
                .build();

        Set<ConstraintViolation<InicioReservaDTO>> violations = validator.validate(inicioReservaDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testInicioReservaDTOInvalidHotelId() {
        InicioReservaDTO inicioReservaDTO = InicioReservaDTO.builder()
                .hotelId(0L)
                .fechaCheckIn(LocalDate.now())
                .fechaCheckOut(LocalDate.now().plusDays(1))
                .duracionDias(1L)
                .habitacionSelecciones(new ArrayList<>())
                .precioTotal(BigDecimal.TEN)
                .build();

        Set<ConstraintViolation<InicioReservaDTO>> violations = validator.validate(inicioReservaDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<InicioReservaDTO> violation = violations.iterator().next();
        assertEquals("hotelId debe ser mayor que 0", violation.getMessage());
    }
}
