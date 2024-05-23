package es.deusto.bilboHotels.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.deusto.bilboHotels.model.Direccion;
import es.deusto.bilboHotels.model.Habitacion;
import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.HotelManager;
import es.deusto.bilboHotels.model.Reserva;

public class HotelTest {
    private Hotel hotel1;
    private Hotel hotel2;
    private Direccion direccion;
    private HotelManager hotelManager;
    private List<Habitacion> habitaciones;
    private List<Reserva> reservas;

    @Before
    public void setUp() {
        direccion = new Direccion(); // Suponiendo que tienes una clase Direccion
        direccion.setId(1L); // Set an ID for the direccion
        
        hotelManager = new HotelManager(); // Suponiendo que tienes una clase HotelManager
        hotelManager.setId(1L); // Set an ID for the hotelManager

        habitaciones = new ArrayList<>();
        Habitacion habitacion = new Habitacion(); // Suponiendo que tienes una clase Habitacion
        habitacion.setId(1L); // Set an ID for the habitacion
        habitaciones.add(habitacion);

        reservas = new ArrayList<>();
        Reserva reserva = new Reserva(); // Suponiendo que tienes una clase Reserva
        reserva.setId(1L); // Set an ID for the reserva
        reservas.add(reserva);

        hotel1 = new Hotel();
        hotel1.setId(1L);
        hotel1.setNombre("Hotel Uno");
        hotel1.setDireccion(direccion);
        hotel1.setHabitaciones(habitaciones);
        hotel1.setReservas(reservas);
        hotel1.setHotelManager(hotelManager);

        hotel2 = new Hotel();
        hotel2.setId(1L);
        hotel2.setNombre("Hotel Uno");
        hotel2.setDireccion(direccion);
        hotel2.setHabitaciones(habitaciones);
        hotel2.setReservas(reservas);
        hotel2.setHotelManager(hotelManager);
    }

    /*@Test
    public void testToString() {
        String expectedString = "Hotel{id=1, nombre='Hotel Uno', address=Direccion{id=1}, habitaciones=[Habitacion{id=1}], hotelManager=HotelManager{id=1}}";
        assertEquals(expectedString, hotel1.toString());
    }*/

    @Test
    public void testEquals() {
        assertTrue(hotel1.equals(hotel2));

        hotel2.setId(2L);
        assertFalse(hotel1.equals(hotel2));
    }

    @Test
    public void testHashCode() {
        assertEquals(hotel1.hashCode(), hotel2.hashCode());

        hotel2.setId(2L);
        assertNotEquals(hotel1.hashCode(), hotel2.hashCode());
    }

    @Test
    public void testSetAndGetId() {
        hotel1.setId(2L);
        assertEquals(Long.valueOf(2L), hotel1.getId());
    }

    @Test
    public void testSetAndGetNombre() {
        hotel1.setNombre("Hotel Dos");
        assertEquals("Hotel Dos", hotel1.getNombre());
    }

    @Test
    public void testSetAndGetDireccion() {
        Direccion newDireccion = new Direccion();
        newDireccion.setId(2L);
        hotel1.setDireccion(newDireccion);
        assertEquals(newDireccion, hotel1.getDireccion());
    }

    @Test
    public void testSetAndGetHabitaciones() {
        List<Habitacion> newHabitaciones = new ArrayList<>();
        hotel1.setHabitaciones(newHabitaciones);
        assertEquals(newHabitaciones, hotel1.getHabitaciones());
    }

    @Test
    public void testSetAndGetReservas() {
        List<Reserva> newReservas = new ArrayList<>();
        hotel1.setReservas(newReservas);
        assertEquals(newReservas, hotel1.getReservas());
    }

    @Test
    public void testSetAndGetHotelManager() {
        HotelManager newHotelManager = new HotelManager();
        newHotelManager.setId(2L);
        hotel1.setHotelManager(newHotelManager);
        assertEquals(newHotelManager, hotel1.getHotelManager());
    }
}
