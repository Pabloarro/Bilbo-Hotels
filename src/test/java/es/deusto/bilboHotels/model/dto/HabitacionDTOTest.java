package es.deusto.bilboHotels.model.dto;

import es.deusto.bilboHotels.model.enums.TipoHabitacion;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class HabitacionDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidHabitacionDTO() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(2)
                .precioPorNoche(100.0)
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertThat(violations).isEmpty();
    }

   /*  @Test
    public void testInvalidContadorHabitacion() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(-1) // Contador de habitaciones negativo
                .precioPorNoche(100.0)
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("contadorHabitacion")
                && v.getMessage().equals("El contador de habitaciones debe ser 0 o más"));
    }*/

    @Test
    public void testInvalidPrecioPorNoche() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(2)
                .precioPorNoche(-10.0) // Precio negativo
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("precioPorNoche")
                && v.getMessage().equals("El precio por noche debe ser 0 o más."));
    }

    @Test
    public void testNullContadorHabitacion() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(null) // Contador de habitaciones nulo
                .precioPorNoche(100.0)
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("contadorHabitacion")
                && v.getMessage().equals("El contador de habitacion no puede estar vacio"));
    }

    @Test
    public void testNullPrecioPorNoche() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(2)
                .precioPorNoche(null) // Precio nulo
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("precioPorNoche")
                && v.getMessage().equals("El preciop no puede estar vacio"));
    }

    @Test
    public void testAllNullValues() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .hotelId(null)
                .tipoHabitacion(null)
                .contadorHabitacion(null)
                .precioPorNoche(null)
                .build();

        Set<ConstraintViolation<HabitacionDTO>> violations = validator.validate(habitacionDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("contadorHabitacion")
                && v.getMessage().equals("El contador de habitacion no puede estar vacio"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("precioPorNoche")
                && v.getMessage().equals("El preciop no puede estar vacio"));
    }
}


