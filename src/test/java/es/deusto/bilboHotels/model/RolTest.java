package es.deusto.bilboHotels.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import es.deusto.bilboHotels.model.Rol;
import es.deusto.bilboHotels.model.enums.TipoRol;

public class RolTest {
    @InjectMocks
    private Rol rol1;
    private Rol rol2;
    private Rol rol3;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rol1 = new Rol(TipoRol.ADMIN);
        rol2 = new Rol(TipoRol.CLIENTE);
        rol3 = new Rol(TipoRol.ADMIN);
    }

    @Test
    public void testConstructor() {
        assertNotNull(rol1);
        assertEquals(TipoRol.ADMIN, rol1.getTipoRol());
    }

    @Test
    public void testEquals() {
        assertTrue(rol1.equals(rol1)); // Reflexive
        assertFalse(rol1.equals(null)); // null check
        assertFalse(rol1.equals(new Object())); // Different class
        assertFalse(rol1.equals(rol2)); // Different TipoRol
        assertTrue(rol1.equals(rol3)); // Same TipoRol and id is null for both
    }

    @Test
    public void testHashCode() {
        Set<Rol> roles = new HashSet<>();
        roles.add(rol1);
        roles.add(rol3);
        assertEquals(1, roles.size()); // Si rol1 y rol3 son iguales, el set solo deberÃ­a tener un elemento
    }

    @Test
    public void testEqualsAndHashCodeConsistency() {
        assertTrue(rol1.equals(rol3));
        assertEquals(rol1.hashCode(), rol3.hashCode());
    }
}
