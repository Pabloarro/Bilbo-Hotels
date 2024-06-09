package es.deusto.bilboHotels.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class HotelDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidHotelDTO() {
        HotelDTO hotelDTO = HotelDTO.builder()
                .nombre("Hotel XYZ")
                .direccionDTO(DireccionDTO.builder()
                        .lineaDireccion("Calle Falsa 123")
                        .ciudad("Bilbao")
                        .pais("Espana")
                        .build())
                .build();

        Set<ConstraintViolation<HotelDTO>> violations = validator.validate(hotelDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testInvalidNombre() {
        HotelDTO hotelDTO = HotelDTO.builder()
                .nombre("") // Nombre vacío
                .direccionDTO(DireccionDTO.builder()
                        .lineaDireccion("Calle Falsa 123")
                        .ciudad("Bilbao")
                        .pais("España")
                        .build())
                .build();

        Set<ConstraintViolation<HotelDTO>> violations = validator.validate(hotelDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("nombre")
                && v.getMessage().equals("El nombre del hotel no puede estar vacio"));
    }

    @Test
    public void testInvalidDireccionDTO() {
        HotelDTO hotelDTO = HotelDTO.builder()
                .nombre("Hotel XYZ")
                .direccionDTO(DireccionDTO.builder()
                        .lineaDireccion("") // Línea de dirección vacía
                        .ciudad("Bilbao")
                        .pais("España")
                        .build())
                .build();

        Set<ConstraintViolation<HotelDTO>> violations = validator.validate(hotelDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("direccionDTO.lineaDireccion")
                && v.getMessage().equals("La línea de dirección no puede estar vacía."));
    }

}
