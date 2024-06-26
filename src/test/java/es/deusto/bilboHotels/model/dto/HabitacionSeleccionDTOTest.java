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

public class HabitacionSeleccionDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidHabitacionSeleccionDTO() {
        HabitacionSeleccionDTO habitacionSeleccionDTO = HabitacionSeleccionDTO.builder()
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contar(2)
                .build();

        Set<ConstraintViolation<HabitacionSeleccionDTO>> violations = validator.validate(habitacionSeleccionDTO);

        assertThat(violations).isEmpty();
    }

   /* @Test
    public void testInvalidContar() {
        HabitacionSeleccionDTO habitacionSeleccionDTO = HabitacionSeleccionDTO.builder()
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contar(-1)
                .build();

        Set<ConstraintViolation<HabitacionSeleccionDTO>> violations = validator.validate(habitacionSeleccionDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("contar")
                && v.getMessage().equals("Debe haber una violación de validación ==> El contar no puede ser negativo"));
    }*/

    @Test
    public void testEqualsAndHashCode() {
        HabitacionSeleccionDTO dto1 = HabitacionSeleccionDTO.builder()
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contar(2)
                .build();

        HabitacionSeleccionDTO dto2 = HabitacionSeleccionDTO.builder()
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contar(2)
                .build();

        HabitacionSeleccionDTO dto3 = HabitacionSeleccionDTO.builder()
                .tipoHabitacion(TipoHabitacion.INDIVIDUAL)
                .contar(1)
                .build();

        assertTrue(dto1.equals(dto2));
        assertTrue(dto2.equals(dto1));
        assertEquals(dto1.hashCode(), dto2.hashCode());

        assertFalse(dto1.equals(dto3));
        assertFalse(dto2.equals(dto3));
    }

    @Test
    public void testToString() {
        HabitacionSeleccionDTO habitacionSeleccionDTO = HabitacionSeleccionDTO.builder()
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contar(2)
                .build();

        String expectedToString = "HabitacionSeleccionDTO(tipoHabitacion=DOBLE, contar=2)";
        assertEquals(expectedToString, habitacionSeleccionDTO.toString());
    }

    @Test
    public void testSettersAndGetters() {
        HabitacionSeleccionDTO habitacionSeleccionDTO = new HabitacionSeleccionDTO();
        habitacionSeleccionDTO.setTipoHabitacion(TipoHabitacion.INDIVIDUAL);
        habitacionSeleccionDTO.setContar(1);

        assertEquals(TipoHabitacion.INDIVIDUAL, habitacionSeleccionDTO.getTipoHabitacion());
        assertEquals(1, habitacionSeleccionDTO.getContar());
    }
}
