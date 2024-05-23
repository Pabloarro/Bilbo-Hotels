package es.deusto.bilboHotels.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class TarjetaPagoDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTarjetaPagoDTOValid() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("Jon Garcia")
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta("12/25")
                .cvc("123")
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    public void testTarjetaPagoDTOInvalidName() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("Jon123")
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta("12/25")
                .cvc("123")
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertEquals(1, violations.size(), "Debe haber una violación de validación");
        ConstraintViolation<TarjetaPagoDTO> violation = violations.iterator().next();
        assertEquals("El nombre del titular solo puede contener letras y espacios", violation.getMessage());
    }
}
