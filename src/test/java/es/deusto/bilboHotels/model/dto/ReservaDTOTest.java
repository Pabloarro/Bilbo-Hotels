package es.deusto.bilboHotels.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ReservaDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testReservaDTOValid() {
        ReservaDTO reservaDTO = ReservaDTO.builder()
                .id(1L)
                .numeroConfirmacion("ABC123")
                .fechaReserva(LocalDateTime.now())
                .clienteId(1L)
                .hotelId(1L)
                .fechaCheckIn(LocalDate.now())
                .fechaCheckOut(LocalDate.now().plusDays(1))
                .seleccionesHabitacion(new ArrayList<>())
                .precioTotal(BigDecimal.TEN)
                .nombreHotel("Hotel Ejemplo")
                .direccionHotel(new DireccionDTO())
                .nombreCliente("Jon Garcia")
                .emailCliente("jon@example.com")
                //.estadoPago(EstadoPago.PENDIENTE)
                //.metodoPago(MetodoPago.TARJETA_CREDITO)
                .build();

        Set<ConstraintViolation<ReservaDTO>> violations = validator.validate(reservaDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testReservaDTOInvalidNumeroConfirmacion() {
        ReservaDTO reservaDTO = ReservaDTO.builder()
                .id(1L)
                .numeroConfirmacion("")
                .fechaReserva(LocalDateTime.now())
                .clienteId(1L)
                .hotelId(1L)
                .fechaCheckIn(LocalDate.now())
                .fechaCheckOut(LocalDate.now().plusDays(1))
                .seleccionesHabitacion(new ArrayList<>())
                .precioTotal(BigDecimal.TEN)
                .nombreHotel("Hotel Ejemplo")
                .direccionHotel(new DireccionDTO())
                .nombreCliente("Jon Garcia")
                .emailCliente("jon@example.com")
                //.estadoPago(EstadoPago.PENDIENTE)
                //.metodoPago(MetodoPago.TARJETA_CREDITO)
                .build();

        Set<ConstraintViolation<ReservaDTO>> violations = validator.validate(reservaDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<ReservaDTO> violation = violations.iterator().next();
        assertEquals("La dirección de correo electrónico no puede estar vacía.", violation.getMessage());
    }
}
