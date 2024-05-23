package es.deusto.bilboHotels.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class AdminTest {
    private Admin admin1;
    private Admin admin2;
    private Usuario usuario1;
    private Usuario usuario2;

    @Before
    public void setUp() {
        usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setUsername("user1");

        usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setUsername("user2");

        admin1 = new Admin();
        admin1.setId(1L);
        admin1.setUsuario(usuario1);

        admin2 = new Admin();
        admin2.setId(1L);
        admin2.setUsuario(usuario1);
    }

    @Test
    public void testToString() {
        String expected = "Admin{id=1, usuario=Usuario{id=1, username='user1'}}";
        assertEquals(expected, admin1.toString());
    }

    @Test
    public void testEquals() {
        assertEquals(admin1, admin2);

        admin2.setUsuario(usuario2);
        assertNotEquals(admin1, admin2);
    }

    @Test
    public void testHashCode() {
        assertEquals(admin1.hashCode(), admin2.hashCode());

        admin2.setUsuario(usuario2);
        assertNotEquals(admin1.hashCode(), admin2.hashCode());
    }

    @Test
    public void testIdNotNull() {
        assertNotNull(admin1.getId());
    }

    @Test
    public void testUsuarioNotNull() {
        assertNotNull(admin1.getUsuario());
    }
}
