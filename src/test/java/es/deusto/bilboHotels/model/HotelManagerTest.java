package es.deusto.bilboHotels.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.HotelManager;
import es.deusto.bilboHotels.model.Usuario;

public class HotelManagerTest {
    private HotelManager hotelManager1;
    private HotelManager hotelManager2;
    private Usuario usuario;
    private List<Hotel> hotelList;

    @Before
    public void setUp() {
        usuario = new Usuario(); // Suponiendo que tienes una clase Usuario
        usuario.setId(1L); // Set an ID for the usuario

        hotelList = new ArrayList<>();
        Hotel hotel = new Hotel(); // Suponiendo que tienes una clase Hotel
        hotel.setId(1L); // Set an ID for the hotel
        hotelList.add(hotel);

        hotelManager1 = new HotelManager();
        hotelManager1.setId(1L);
        hotelManager1.setUsuario(usuario);
        hotelManager1.setHotelList(hotelList);

        hotelManager2 = new HotelManager();
        hotelManager2.setId(1L);
        hotelManager2.setUsuario(usuario);
        hotelManager2.setHotelList(hotelList);
    }

    @Test
    public void testToString() {
        String expectedString = "HotelManager{id=1, usuario=Usuario{id=1}, hotelList=[Hotel{id=1}]}";
        assertEquals(expectedString, hotelManager1.toString());
    }

    @Test
    public void testEquals() {
        assertTrue(hotelManager1.equals(hotelManager2));

        hotelManager2.setId(2L);
        assertFalse(hotelManager1.equals(hotelManager2));
    }

    @Test
    public void testHashCode() {
        assertEquals(hotelManager1.hashCode(), hotelManager2.hashCode());

        hotelManager2.setId(2L);
        assertNotEquals(hotelManager1.hashCode(), hotelManager2.hashCode());
    }

    @Test
    public void testSetAndGetId() {
        hotelManager1.setId(2L);
        assertEquals(Long.valueOf(2L), hotelManager1.getId());
    }

    @Test
    public void testSetAndGetUsuario() {
        Usuario newUsuario = new Usuario();
        newUsuario.setId(2L);
        hotelManager1.setUsuario(newUsuario);
        assertEquals(newUsuario, hotelManager1.getUsuario());
    }

    @Test
    public void testSetAndGetHotelList() {
        List<Hotel> newHotelList = new ArrayList<>();
        hotelManager1.setHotelList(newHotelList);
        assertEquals(newHotelList, hotelManager1.getHotelList());
    }
}
