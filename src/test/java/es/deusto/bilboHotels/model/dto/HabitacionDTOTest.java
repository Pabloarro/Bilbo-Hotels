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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HabitacionDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testSetContadorHabitacion() {
        HabitacionDTO habitacionDTO = new HabitacionDTO();
        habitacionDTO.setContadorHabitacion(2);

        assertEquals(2, habitacionDTO.getContadorHabitacion());
    }

    @Test
    public void testSetPrecioPorNoche() {
        HabitacionDTO habitacionDTO = new HabitacionDTO();
        habitacionDTO.setPrecioPorNoche(100.0);

        assertEquals(100.0, habitacionDTO.getPrecioPorNoche());
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

    @Test
    public void testSettersAndGetters() {
        HabitacionDTO habitacionDTO = new HabitacionDTO();
        habitacionDTO.setId(1L);
        habitacionDTO.setHotelId(1L);
        habitacionDTO.setTipoHabitacion(TipoHabitacion.DOBLE);

        assertThat(habitacionDTO.getId()).isEqualTo(1L);
        assertThat(habitacionDTO.getHotelId()).isEqualTo(1L);
        assertThat(habitacionDTO.getTipoHabitacion()).isEqualTo(TipoHabitacion.DOBLE);
    }

    @Test
    public void testEqualsAndHashCode() {
        HabitacionDTO habitacionDTO1 = HabitacionDTO.builder()
                .id(1L)
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(2)
                .precioPorNoche(100.0)
                .build();

        HabitacionDTO habitacionDTO2 = HabitacionDTO.builder()
                .id(1L)
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(2)
                .precioPorNoche(100.0)
                .build();

        assertThat(habitacionDTO1).isEqualTo(habitacionDTO2);
        assertThat(habitacionDTO1.hashCode()).isEqualTo(habitacionDTO2.hashCode());
    }

    @Test
    public void testNotEqualsAndHashCode() {
        HabitacionDTO habitacionDTO1 = HabitacionDTO.builder()
                .id(1L)
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(2)
                .precioPorNoche(100.0)
                .build();

        HabitacionDTO habitacionDTO2 = HabitacionDTO.builder()
                .id(2L)
                .hotelId(2L)
                .tipoHabitacion(TipoHabitacion.INDIVIDUAL)
                .contadorHabitacion(3)
                .precioPorNoche(150.0)
                .build();

        assertThat(habitacionDTO1).isNotEqualTo(habitacionDTO2);
        assertThat(habitacionDTO1.hashCode()).isNotEqualTo(habitacionDTO2.hashCode());
    }

    @Test
    public void testToString() {
        HabitacionDTO habitacionDTO = HabitacionDTO.builder()
                .id(1L)
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(2)
                .precioPorNoche(100.0)
                .build();

        String expectedToString = "HabitacionDTO(id=1, hotelId=1, tipoHabitacion=DOBLE, contadorHabitacion=2, precioPorNoche=100.0)";
        assertEquals(expectedToString, habitacionDTO.toString());
    }
}


