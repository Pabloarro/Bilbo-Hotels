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
}
