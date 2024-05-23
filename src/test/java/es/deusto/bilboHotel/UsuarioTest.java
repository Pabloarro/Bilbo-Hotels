package es.deusto.bilboHotel;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import es.deusto.bilboHotels.model.Rol;
import es.deusto.bilboHotels.model.Usuario;
import es.deusto.bilboHotels.model.enums.TipoRol;

public class UsuarioTest {
    private Usuario usuario1;
    private Usuario usuario2;
    private Usuario usuario3;
    private Rol rol;

    @Before
    public void setUp() {
        rol = new Rol(TipoRol.ADMIN); // Asegúrate de que el constructor de Rol y TipoRol estén definidos
        usuario1 = new Usuario();
        usuario1.setUsername("user1");
        usuario1.setPassword("pass1");
        usuario1.setNombre("Nombre1");
        usuario1.setApellido("Apellido1");
        usuario1.setRol(rol);
        usuario1.setCreatedDate(LocalDateTime.now());

        usuario2 = new Usuario();
        usuario2.setUsername("user2");
        usuario2.setPassword("pass2");
        usuario2.setNombre("Nombre2");
        usuario2.setApellido("Apellido2");
        usuario2.setRol(rol);
        usuario2.setCreatedDate(LocalDateTime.now());

        usuario3 = new Usuario();
        usuario3.setUsername("user1");
        usuario3.setPassword("pass3");
        usuario3.setNombre("Nombre3");
        usuario3.setApellido("Apellido3");
        usuario3.setRol(rol);
        usuario3.setCreatedDate(usuario1.getCreatedDate());
    }

    @Test
    public void testToString() {
        String expected = "Usuario{id=null, username='user1', createdDate=" + usuario1.getCreatedDate() + ", nombre='Nombre1', apellido='Apellido1', rol=" + rol + "}";
        assertEquals(expected, usuario1.toString());
    }

    @Test
    public void testEquals() {
        assertTrue(usuario1.equals(usuario1)); 
        assertFalse(usuario1.equals(null)); 
        assertFalse(usuario1.equals(new Object())); 
        assertFalse(usuario1.equals(usuario2)); // Diferente nombre de usuario
        assertTrue(usuario1.equals(usuario3)); 
    }

    @Test
    public void testHashCode() {
        assertEquals(usuario1.hashCode(), usuario3.hashCode()); // Mismo nombre de usuario y fecha
        assertNotEquals(usuario1.hashCode(), usuario2.hashCode()); // Different nombre de usuario
    }

    @Test
    public void testEqualsAndHashCodeConsistency() {
        assertTrue(usuario1.equals(usuario3));
        assertEquals(usuario1.hashCode(), usuario3.hashCode());
    }
}
