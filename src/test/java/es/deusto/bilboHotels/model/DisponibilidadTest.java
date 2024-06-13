package es.deusto.bilboHotels.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.deusto.bilboHotels.model.Disponibilidad;
import es.deusto.bilboHotels.model.Habitacion;
import es.deusto.bilboHotels.model.Hotel;

public class DisponibilidadTest {
    @InjectMocks
    private Disponibilidad disponibilidad1;
    private Disponibilidad disponibilidad2;
    @Mock
    private Hotel hotel1;
    private Hotel hotel2;
    private Habitacion habitacion1;
    private Habitacion habitacion2;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        hotel1 = new Hotel();
        hotel1.setId(1L);
        hotel1.setNombre("Hotel1");

        hotel2 = new Hotel();
        hotel2.setId(2L);
        hotel2.setNombre("Hotel2");

        habitacion1 = new Habitacion();
        habitacion1.setId(1L);
        //habitacion1.setTipo("Single");

        habitacion2 = new Habitacion();
        habitacion2.setId(2L);
        //habitacion2.setTipo("Double");

        disponibilidad1 = new Disponibilidad();
        disponibilidad1.setId(1L);
        disponibilidad1.setHotel(hotel1);
        disponibilidad1.setFecha(LocalDate.of(2023, 5, 22));
        disponibilidad1.setHabitacion(habitacion1);
        disponibilidad1.setHabitacionesDisponibles(5);

        disponibilidad2 = new Disponibilidad();
        disponibilidad2.setId(1L);
        disponibilidad2.setHotel(hotel1);
        disponibilidad2.setFecha(LocalDate.of(2023, 5, 22));
        disponibilidad2.setHabitacion(habitacion1);
        disponibilidad2.setHabitacionesDisponibles(5);
    }

    /*@Test
    public void testToString() {
        String expected = "Disponibilidad{id=1, hotel=Hotel{id=1, nombre='Hotel1'}, fecha=2023-05-22, habitacion=Habitacion{id=1, tipo='Single'}, habitacionesDisponibles=5}";
        assertEquals(expected, disponibilidad1.toString());
    }*/

    @Test
    public void testEquals() {
        assertEquals(disponibilidad1, disponibilidad2);

        disponibilidad2.setHotel(hotel2);
        assertNotEquals(disponibilidad1, disponibilidad2);
    }

    @Test
    public void testHashCode() {
        assertEquals(disponibilidad1.hashCode(), disponibilidad2.hashCode());

        disponibilidad2.setHotel(hotel2);
        assertNotEquals(disponibilidad1.hashCode(), disponibilidad2.hashCode());
    }

    @Test
    public void testIdNotNull() {
        assertNotNull(disponibilidad1.getId());
    }

    @Test
    public void testHotelNotNull() {
        assertNotNull(disponibilidad1.getHotel());
    }

    @Test
    public void testFechaNotNull() {
        assertNotNull(disponibilidad1.getFecha());
    }

    @Test
    public void testHabitacionNotNull() {
        assertNotNull(disponibilidad1.getHabitacion());
    }

    @Test
    public void testHabitacionesDisponibles() {
        assertTrue(disponibilidad1.getHabitacionesDisponibles() > 0);
    }
}
