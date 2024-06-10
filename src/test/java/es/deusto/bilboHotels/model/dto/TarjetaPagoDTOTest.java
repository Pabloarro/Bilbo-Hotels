package es.deusto.bilboHotels.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TarjetaPagoDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidTarjetaPagoDTO() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("John Doe")
                .numeroTarjeta("4111111111111111") // Visa test card number
                .caducidadTarjeta("12/25") // Caducidad válida
                .cvc("123")
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidNombreTitular() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("1234") // Nombre inválido
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta("12/25")
                .cvc("123")
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidNumeroTarjeta() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("John Doe")
                .numeroTarjeta("1234") // Número de tarjeta inválido
                .caducidadTarjeta("12/25")
                .cvc("123")
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidCaducidadTarjeta() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("John Doe")
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta("12/20") // Caducidad anterior a la fecha actual
                .cvc("123")
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidCvc() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("John Doe")
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta(YearMonth.now().plusMonths(1).toString())
                .cvc("12") // CVC inválido
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertFalse(violations.isEmpty());
    }
}
