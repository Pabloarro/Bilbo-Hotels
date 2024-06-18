package es.deusto.bilboHotels.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void testBlankNombreTitular() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("") // Nombre vacío
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta("12/25")
                .cvc("123")
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void testMinLengthNombreTitular() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("Jo") // Nombre demasiado corto
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta("12/25")
                .cvc("123")
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El nombre del titular tiene qye tener entre 3 and 50 characters")));
    }

    @Test
    public void testMaxLengthNombreTitular() {
        String longName = "John".repeat(20); // Nombre demasiado largo
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular(longName)
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta("12/25")
                .cvc("123")
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El nombre del titular tiene qye tener entre 3 and 50 characters")));
    }

    @Test
    public void testNullCaducidadTarjeta() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("John Doe")
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta(null) // Caducidad nula
                .cvc("123")
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNonNumericCvc() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("John Doe")
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta("12/25")
                .cvc("12a") // CVC no numérico
                .build();

        Set<ConstraintViolation<TarjetaPagoDTO>> violations = validator.validate(tarjetaPagoDTO);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("CVC tiene que ser de 3 digitos")));
    }

    @Test
    public void testGettersAndSetters() {
        TarjetaPagoDTO tarjetaPagoDTO = new TarjetaPagoDTO();
        tarjetaPagoDTO.setNombreTitular("Jane Doe");
        tarjetaPagoDTO.setNumeroTarjeta("4111111111111111");
        tarjetaPagoDTO.setCaducidadTarjeta("12/25");
        tarjetaPagoDTO.setCvc("123");

        assertThat(tarjetaPagoDTO.getNombreTitular()).isEqualTo("Jane Doe");
        assertThat(tarjetaPagoDTO.getNumeroTarjeta()).isEqualTo("4111111111111111");
        assertThat(tarjetaPagoDTO.getCaducidadTarjeta()).isEqualTo("12/25");
        assertThat(tarjetaPagoDTO.getCvc()).isEqualTo("123");
    }

    // Pruebas para equals y hashCode
    @Test
    public void testEqualsAndHashCode() {
        TarjetaPagoDTO tarjeta1 = TarjetaPagoDTO.builder()
                .nombreTitular("John Doe")
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta("12/25")
                .cvc("123")
                .build();

        TarjetaPagoDTO tarjeta2 = TarjetaPagoDTO.builder()
                .nombreTitular("John Doe")
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta("12/25")
                .cvc("123")
                .build();

        TarjetaPagoDTO tarjeta3 = TarjetaPagoDTO.builder()
                .nombreTitular("Jane Doe")
                .numeroTarjeta("4111111111111112")
                .caducidadTarjeta("11/24")
                .cvc("124")
                .build();

        assertThat(tarjeta1).isEqualTo(tarjeta2);
        assertThat(tarjeta1).isNotEqualTo(tarjeta3);
        assertThat(tarjeta1.hashCode()).isEqualTo(tarjeta2.hashCode());
        assertThat(tarjeta1.hashCode()).isNotEqualTo(tarjeta3.hashCode());
    }

    // Prueba para el método toString
    @Test
    public void testToString() {
        TarjetaPagoDTO tarjetaPagoDTO = TarjetaPagoDTO.builder()
                .nombreTitular("John Doe")
                .numeroTarjeta("4111111111111111")
                .caducidadTarjeta("12/25")
                .cvc("123")
                .build();

        String expectedToString = "TarjetaPagoDTO(nombreTitular=John Doe, numeroTarjeta=4111111111111111, caducidadTarjeta=12/25, cvc=123)";

        assertThat(tarjetaPagoDTO.toString()).isEqualTo(expectedToString);
    }
}
