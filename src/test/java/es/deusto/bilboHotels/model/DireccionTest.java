package es.deusto.bilboHotels.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import es.deusto.bilboHotels.model.Cliente;
import es.deusto.bilboHotels.model.Reserva;
import es.deusto.bilboHotels.model.Usuario;

public class DireccionTest {
    private Cliente cliente1;
    private Cliente cliente2;
    private Usuario usuario1;
    private Usuario usuario2;
    private Reserva reserva1;
    private Reserva reserva2;

    @Before
    public void setUp() {
        usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setUsername("user1");

        usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setUsername("user2");

        reserva1 = new Reserva();
        reserva1.setId(1L);

        reserva2 = new Reserva();
        reserva2.setId(2L);

        List<Reserva> reservaList1 = new ArrayList<>();
        reservaList1.add(reserva1);

        cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setUsuario(usuario1);
        cliente1.setReservaList(reservaList1);

        cliente2 = new Cliente();
        cliente2.setId(1L);
        cliente2.setUsuario(usuario1);
        cliente2.setReservaList(reservaList1);
    }

    @Test
    public void testToString() {
        String expected = "Cliente{id=1, usuario=Usuario{id=1, username='user1'}}";
        assertEquals(expected, cliente1.toString());
    }

    @Test
    public void testEquals() {
        assertEquals(cliente1, cliente2);

        cliente2.setUsuario(usuario2);
        assertNotEquals(cliente1, cliente2);
    }

    @Test
    public void testHashCode() {
        assertEquals(cliente1.hashCode(), cliente2.hashCode());

        cliente2.setUsuario(usuario2);
        assertNotEquals(cliente1.hashCode(), cliente2.hashCode());
    }

    @Test
    public void testIdNotNull() {
        assertNotNull(cliente1.getId());
    }

    @Test
    public void testUsuarioNotNull() {
        assertNotNull(cliente1.getUsuario());
    }

    @Test
    public void testReservaListNotNull() {
        assertNotNull(cliente1.getReservaList());
        assertFalse(cliente1.getReservaList().isEmpty());
    }
}
