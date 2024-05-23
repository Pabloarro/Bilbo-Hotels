package es.deusto.bilboHotel;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.Before;

import es.deusto.bilboHotels.model.HabitacionReservada;
import es.deusto.bilboHotels.model.Reserva;
import es.deusto.bilboHotels.model.enums.TipoHabitacion;

public class HabitacionReservadaTest {
    private HabitacionReservada habitacionReservada1;
    private HabitacionReservada habitacionReservada2;
    private Reserva reserva1;
    private Reserva reserva2;

    @Before
    public void setUp() {
        reserva1 = new Reserva();
        reserva1.setId(1L);

        reserva2 = new Reserva();
        reserva2.setId(2L);

        habitacionReservada1 = new HabitacionReservada();
        habitacionReservada1.setId(1L);
        habitacionReservada1.setReserva(reserva1);
        habitacionReservada1.setTipoHabitacion(TipoHabitacion.INDIVIDUAL);
        habitacionReservada1.setContar(2);

        habitacionReservada2 = new HabitacionReservada();
        habitacionReservada2.setId(1L);
        habitacionReservada2.setReserva(reserva1);
        habitacionReservada2.setTipoHabitacion(TipoHabitacion.INDIVIDUAL);
        habitacionReservada2.setContar(2);
    }

    @Test
    public void testToString() {
        String expected = "HabitacionReservada{id=1, reserva=Reserva{id=1}, tipoHabitacion=SINGLE, contar=2}";
        assertEquals(expected, habitacionReservada1.toString());
    }

    @Test
    public void testEquals() {
        assertEquals(habitacionReservada1, habitacionReservada2);

        habitacionReservada2.setReserva(reserva2);
        assertNotEquals(habitacionReservada1, habitacionReservada2);
    }

    @Test
    public void testHashCode() {
        assertEquals(habitacionReservada1.hashCode(), habitacionReservada2.hashCode());

        habitacionReservada2.setReserva(reserva2);
        assertNotEquals(habitacionReservada1.hashCode(), habitacionReservada2.hashCode());
    }

    @Test
    public void testIdNotNull() {
        assertNotNull(habitacionReservada1.getId());
    }

    @Test
    public void testReservaNotNull() {
        assertNotNull(habitacionReservada1.getReserva());
    }

    @Test
    public void testTipoHabitacionNotNull() {
        assertNotNull(habitacionReservada1.getTipoHabitacion());
    }

    @Test
    public void testContarPositive() {
        assertTrue(habitacionReservada1.getContar() > 0);
    }
}
