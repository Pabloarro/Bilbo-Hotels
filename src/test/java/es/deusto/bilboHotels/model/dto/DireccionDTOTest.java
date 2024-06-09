package es.deusto.bilboHotels.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DireccionDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenLineaDireccionIsBlank_thenValidationFails() {
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setLineaDireccion("");
        direccionDTO.setCiudad("Bilbao");
        direccionDTO.setPais("España");

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccionDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("lineaDireccion")
                && v.getMessage().equals("La línea de dirección no puede estar vacía."));
    }

    @Test
    public void whenCiudadIsBlank_thenValidationFails() {
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setLineaDireccion("Calle Falsa 123");
        direccionDTO.setCiudad("");
        direccionDTO.setPais("España");

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccionDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("ciudad")
                && v.getMessage().equals("La ciudad no puede estar vacia"));
    }

    @Test
    public void whenPaisIsBlank_thenValidationFails() {
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setLineaDireccion("Calle Falsa 123");
        direccionDTO.setCiudad("Bilbao");
        direccionDTO.setPais("");

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccionDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("pais")
                && v.getMessage().equals("El país no puede estar vacío"));
    }

    @Test
    public void whenAllFieldsAreValid_thenValidationSucceeds() {
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setLineaDireccion("Calle Falsa 123");
        direccionDTO.setCiudad("Bilbao");
        direccionDTO.setPais("Espana");

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccionDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    public void whenInvalidCiudad_thenValidationFails() {
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setLineaDireccion("Calle Falsa 123");
        direccionDTO.setCiudad("12345");
        direccionDTO.setPais("España");

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccionDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("ciudad")
                && v.getMessage().equals("La ciudad solo puede contener letras"));
    }

    @Test
    public void whenInvalidLineaDireccion_thenValidationFails() {
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setLineaDireccion("Calle Falsa 123!!");
        direccionDTO.setCiudad("Bilbao");
        direccionDTO.setPais("España");

        Set<ConstraintViolation<DireccionDTO>> violations = validator.validate(direccionDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("lineaDireccion")
                && v.getMessage().equals("La línea de dirección solo puede contener letras, números y algunos caracteres especiales (., :, -)."));
    }
}
