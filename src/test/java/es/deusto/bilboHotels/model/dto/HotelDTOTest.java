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
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import es.deusto.bilboHotels.model.enums.TipoHabitacion;  // Importar TipoHabitacion

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

    @Test
    public void testEqualsAndHashCode() {
        DireccionDTO direccion1 = DireccionDTO.builder()
                .lineaDireccion("Calle Falsa 123")
                .ciudad("Bilbao")
                .pais("España")
                .build();

        List<HabitacionDTO> habitaciones1 = new ArrayList<>();
        habitaciones1.add(HabitacionDTO.builder()
                .id(1L)
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(101)
                .precioPorNoche(150.0)
                .build());

        HotelDTO hotel1 = HotelDTO.builder()
                .id(1L)
                .nombre("Hotel XYZ")
                .direccionDTO(direccion1)
                .habitacionDTOs(habitaciones1)
                .managerUsername("manager1")
                .build();

        HotelDTO hotel2 = HotelDTO.builder()
                .id(1L)
                .nombre("Hotel XYZ")
                .direccionDTO(direccion1)
                .habitacionDTOs(habitaciones1)
                .managerUsername("manager1")
                .build();

        assertEquals(hotel1, hotel2);
        assertEquals(hotel1.hashCode(), hotel2.hashCode());
    }

    @Test
    public void testToString() {
        HotelDTO hotel = HotelDTO.builder()
                .id(1L)
                .nombre("Hotel XYZ")
                .direccionDTO(DireccionDTO.builder()
                        .lineaDireccion("Calle Falsa 123")
                        .ciudad("Bilbao")
                        .pais("España")
                        .build())
                .managerUsername("manager1")
                .build();

        String expectedToString = "HotelDTO{id=1, nombre='Hotel XYZ', direccionDTO=DireccionDTO{lineaDireccion='Calle Falsa 123', ciudad='Bilbao', pais='España'}, habitacionDTOs=null, managerUsername='manager1'}";
        assertEquals(expectedToString, hotel.toString());
    }

    @Test
    public void testGettersAndSetters() {
        HotelDTO hotel = HotelDTO.builder().build();

        DireccionDTO direccion = DireccionDTO.builder()
                .lineaDireccion("Calle Falsa 123")
                .ciudad("Bilbao")
                .pais("España")
                .build();

        List<HabitacionDTO> habitaciones = new ArrayList<>();
        habitaciones.add(HabitacionDTO.builder()
                .id(1L)
                .hotelId(1L)
                .tipoHabitacion(TipoHabitacion.DOBLE)
                .contadorHabitacion(101)
                .precioPorNoche(150.0)
                .build());

        hotel.setId(1L);
        hotel.setNombre("Hotel XYZ");
        hotel.setDireccionDTO(direccion);
        hotel.setHabitacionDTOs(habitaciones);
        hotel.setManagerUsername("manager1");

        assertEquals(1L, hotel.getId());
        assertEquals("Hotel XYZ", hotel.getNombre());
        assertEquals(direccion, hotel.getDireccionDTO());
        assertEquals(habitaciones, hotel.getHabitacionDTOs());
        assertEquals("manager1", hotel.getManagerUsername());
    }

    @Test
    public void testConstructorDefault() {
        HotelDTO hotelDTO = new HotelDTO();
        assertNull(hotelDTO.getId());
        assertNull(hotelDTO.getNombre());
        assertNull(hotelDTO.getDireccionDTO());
        assertNotNull(hotelDTO.getHabitacionDTOs());
        assertTrue(hotelDTO.getHabitacionDTOs().isEmpty());
        assertNull(hotelDTO.getManagerUsername());
    }

}
