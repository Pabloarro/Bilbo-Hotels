package es.deusto.bilboHotel;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import es.deusto.bilboHotels.model.Cliente;
import es.deusto.bilboHotels.model.HabitacionReservada;
import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.Pago;
import es.deusto.bilboHotels.model.Reserva;

public class ReservaTest {
    private Reserva reserva;
    private Cliente cliente;
    private Hotel hotel;
    private List<HabitacionReservada> habitacionReservadas;

    @Before
    public void setUp() {
        cliente = new Cliente();
        hotel = new Hotel();
        habitacionReservadas = new ArrayList<>();

        reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setHotel(hotel);
        reserva.setFechaCheckIn(LocalDate.of(2023, 5, 20));
        reserva.setFechaCheckOut(LocalDate.of(2023, 5, 25));
        reserva.setHabitacionReservadas(habitacionReservadas);
    }

    @Test
    public void testOnCreate() {
        //reserva.onCreate();
        assertNotNull(reserva.getNumeroConfirmacion());
        assertEquals(8, reserva.getNumeroConfirmacion().length());
    }

    @Test
    public void testToString() {
        //reserva.onCreate(); 
        String expected = "Reserva{" +
                "id=" + reserva.getId() +
                ", numeroConfirmacion='" + reserva.getNumeroConfirmacion() + '\'' +
                ", fechaReserva=" + reserva.getFechaReserva() +
                ", cliente=" + reserva.getCliente() +
                ", hotel=" + reserva.getHotel() +
                ", fechaCheckIn=" + reserva.getFechaCheckIn() +
                ", fechaCheckOut=" + reserva.getFechaCheckOut() +
                ", habitacionReservadas=" + reserva.getHabitacionReservadas() +
                ", pago=" + reserva.getPago() +
                '}';
        assertEquals(expected, reserva.toString());
    }

    @Test
    public void testEquals() {
        Reserva reserva2 = new Reserva();
        reserva2.setId(reserva.getId());
        reserva2.setNumeroConfirmacion(reserva.getNumeroConfirmacion());

        assertTrue(reserva.equals(reserva2));
        reserva2.setNumeroConfirmacion(UUID.randomUUID().toString().substring(0, 8));
        assertFalse(reserva.equals(reserva2));
    }

    @Test
    public void testHashCode() {
        Reserva reserva2 = new Reserva();
        reserva2.setId(reserva.getId());
        reserva2.setNumeroConfirmacion(reserva.getNumeroConfirmacion());

        assertEquals(reserva.hashCode(), reserva2.hashCode());
        reserva2.setNumeroConfirmacion(UUID.randomUUID().toString().substring(0, 8));
        assertNotEquals(reserva.hashCode(), reserva2.hashCode());
    }

    @Test
    public void testGettersAndSetters() {
        Long id = 1L;
        LocalDateTime fechaReserva = LocalDateTime.now();
        Pago pago = new Pago();

        reserva.setId(id);
        reserva.setFechaReserva(fechaReserva);
        reserva.setPago(pago);

        assertEquals(id, reserva.getId());
        assertEquals(fechaReserva, reserva.getFechaReserva());
        assertEquals(pago, reserva.getPago());
        assertEquals(cliente, reserva.getCliente());
        assertEquals(hotel, reserva.getHotel());
        assertEquals(LocalDate.of(2023, 5, 20), reserva.getFechaCheckIn());
        assertEquals(LocalDate.of(2023, 5, 25), reserva.getFechaCheckOut());
        assertEquals(habitacionReservadas, reserva.getHabitacionReservadas());
    }
}
