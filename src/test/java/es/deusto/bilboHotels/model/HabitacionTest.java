package es.deusto.bilboHotels.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.deusto.bilboHotels.model.Disponibilidad;
import es.deusto.bilboHotels.model.Habitacion;
import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.enums.TipoHabitacion;

public class HabitacionTest {
    @InjectMocks
    private Habitacion habitacion1;
    private Habitacion habitacion2;
    @Mock
    private Hotel hotel;
    private List<Disponibilidad> availabilities;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        hotel = new Hotel(); // Suponiendo que tienes una clase Hotel
        hotel.setId(1L); // Set an ID for the hotel
        
        availabilities = new ArrayList<>();
        
        habitacion1 = new Habitacion();
        habitacion1.setId(1L);
        habitacion1.setHotel(hotel);
        habitacion1.setTipoHabitacion(TipoHabitacion.INDIVIDUAL);
        habitacion1.setContadorHabitacion(101);
        habitacion1.setPrecioPorNoche(150.0);
        habitacion1.setAvailabilities(availabilities);
        
        habitacion2 = new Habitacion();
        habitacion2.setId(1L);
        habitacion2.setHotel(hotel);
        habitacion2.setTipoHabitacion(TipoHabitacion.INDIVIDUAL);
        habitacion2.setContadorHabitacion(101);
        habitacion2.setPrecioPorNoche(150.0);
        habitacion2.setAvailabilities(availabilities);
    }

    /*@Test
    public void testToString() {
        String expectedString = "Habitacion{id=1, hotel=Hotel{id=1}, tipoHabitacion=SINGLE, contadorHabitacion=101, precioPorNoche=150.0}";
        assertEquals(expectedString, habitacion1.toString());
    }*/

    @Test
    public void testEquals() {
        assertTrue(habitacion1.equals(habitacion2));
        
        habitacion2.setId(2L);
        assertFalse(habitacion1.equals(habitacion2));
    }

    @Test
    public void testHashCode() {
        assertEquals(habitacion1.hashCode(), habitacion2.hashCode());
        
        habitacion2.setId(2L);
        assertNotEquals(habitacion1.hashCode(), habitacion2.hashCode());
    }

    @Test
    public void testSetAndGetId() {
        habitacion1.setId(2L);
        assertEquals(Long.valueOf(2L), habitacion1.getId());
    }

    @Test
    public void testSetAndGetHotel() {
        Hotel newHotel = new Hotel();
        newHotel.setId(2L);
        habitacion1.setHotel(newHotel);
        assertEquals(newHotel, habitacion1.getHotel());
    }

    @Test
    public void testSetAndGetTipoHabitacion() {
        habitacion1.setTipoHabitacion(TipoHabitacion.DOBLE);
        assertEquals(TipoHabitacion.DOBLE, habitacion1.getTipoHabitacion());
    }

    @Test
    public void testSetAndGetContadorHabitacion() {
        habitacion1.setContadorHabitacion(202);
        assertEquals(202, habitacion1.getContadorHabitacion());
    }

    @Test
    public void testSetAndGetPrecioPorNoche() {
        habitacion1.setPrecioPorNoche(200.0);
        assertEquals(200.0, habitacion1.getPrecioPorNoche(), 0.0);
    }

    @Test
    public void testSetAndGetAvailabilities() {
        List<Disponibilidad> newAvailabilities = new ArrayList<>();
        habitacion1.setAvailabilities(newAvailabilities);
        assertEquals(newAvailabilities, habitacion1.getAvailabilities());
    }
}
