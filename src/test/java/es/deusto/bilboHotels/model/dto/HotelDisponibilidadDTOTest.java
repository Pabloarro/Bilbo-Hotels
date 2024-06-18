package es.deusto.bilboHotels.model.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class HotelDisponibilidadDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidHotelDisponibilidadDTO() {
        HotelDisponibilidadDTO hotelDisponibilidadDTO = HotelDisponibilidadDTO.builder()
                .nombre("Hotel XYZ")
                .direccionDTO(DireccionDTO.builder()
                        .lineaDireccion("Calle Falsa 123")
                        .ciudad("Bilbao")
                        .pais("España")
                        .build())
                .maxHabitacionesIndividualDisponibles(10)
                .maxHabitacionesDoblesDisponibles(20)
                .build();

        Set<ConstraintViolation<HotelDisponibilidadDTO>> violations = validator.validate(hotelDisponibilidadDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        DireccionDTO direccion1 = DireccionDTO.builder()
                .lineaDireccion("Calle Falsa 123")
                .ciudad("Bilbao")
                .pais("España")
                .build();

        DireccionDTO direccion2 = DireccionDTO.builder()
                .lineaDireccion("Av. Real 456")
                .ciudad("Madrid")
                .pais("España")
                .build();

        HotelDisponibilidadDTO hotel1 = HotelDisponibilidadDTO.builder()
                .id(1L)
                .nombre("Hotel A")
                .direccionDTO(direccion1)
                .maxHabitacionesIndividualDisponibles(10)
                .maxHabitacionesDoblesDisponibles(20)
                .build();

        HotelDisponibilidadDTO hotel2 = HotelDisponibilidadDTO.builder()
                .id(1L)
                .nombre("Hotel A")
                .direccionDTO(direccion1)
                .maxHabitacionesIndividualDisponibles(10)
                .maxHabitacionesDoblesDisponibles(20)
                .build();

        HotelDisponibilidadDTO hotel3 = HotelDisponibilidadDTO.builder()
                .id(2L)
                .nombre("Hotel B")
                .direccionDTO(direccion2)
                .maxHabitacionesIndividualDisponibles(5)
                .maxHabitacionesDoblesDisponibles(15)
                .build();

        // Act & Assert
        assertEquals(hotel1, hotel2);
        assertEquals(hotel1.hashCode(), hotel2.hashCode());

        assertNotEquals(hotel1, hotel3);
        assertNotEquals(hotel1.hashCode(), hotel3.hashCode());
    }

    @Test
public void testToString() {
    // Arrange
    DireccionDTO direccion = DireccionDTO.builder()
            .lineaDireccion("Calle Falsa 123")
            .ciudad("Bilbao")
            .pais("España")
            .build();

    HotelDisponibilidadDTO hotelDisponibilidadDTO = HotelDisponibilidadDTO.builder()
            .id(1L)
            .nombre("Hotel XYZ")
            .direccionDTO(direccion)
            .maxHabitacionesIndividualDisponibles(10)
            .maxHabitacionesDoblesDisponibles(20)
            .build();

    // Act
    String toString = hotelDisponibilidadDTO.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("id=1"));
    assertTrue(toString.contains("nombre='Hotel XYZ'"));
    assertTrue(toString.contains("direccionDTO=DireccionDTO{lineaDireccion='Calle Falsa 123', ciudad='Bilbao', pais='España'}"));
    assertTrue(toString.contains("maxHabitacionesIndividualDisponibles=10"));
    assertTrue(toString.contains("maxHabitacionesDoblesDisponibles=20"));
}



    @Test
    public void testSettersAndGetters() {
        // Arrange
        DireccionDTO direccionDTO = DireccionDTO.builder()
                .lineaDireccion("Calle Falsa 123")
                .ciudad("Bilbao")
                .pais("España")
                .build();

        HotelDisponibilidadDTO hotelDisponibilidadDTO = HotelDisponibilidadDTO.builder()
                .id(1L)
                .nombre("Hotel XYZ")
                .direccionDTO(direccionDTO)
                .maxHabitacionesIndividualDisponibles(10)
                .maxHabitacionesDoblesDisponibles(20)
                .build();

        // Act & Assert
        // Act
        hotelDisponibilidadDTO.setId(1L);
        hotelDisponibilidadDTO.setNombre("Hotel XYZ");
        hotelDisponibilidadDTO.setDireccionDTO(direccionDTO);
        hotelDisponibilidadDTO.setMaxHabitacionesIndividualDisponibles(10);
        hotelDisponibilidadDTO.setMaxHabitacionesDoblesDisponibles(20);

        // Assert
        assertEquals(1L, hotelDisponibilidadDTO.getId());
        assertEquals("Hotel XYZ", hotelDisponibilidadDTO.getNombre());
        assertEquals(direccionDTO, hotelDisponibilidadDTO.getDireccionDTO());
        assertEquals(10, hotelDisponibilidadDTO.getMaxHabitacionesIndividualDisponibles());
        assertEquals(20, hotelDisponibilidadDTO.getMaxHabitacionesDoblesDisponibles());
    }
}
