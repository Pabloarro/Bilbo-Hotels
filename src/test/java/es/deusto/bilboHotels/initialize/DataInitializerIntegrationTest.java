/*package es.deusto.bilboHotels.initialize;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import es.deusto.bilboHotels.repository.*;
import es.deusto.bilboHotels.model.*;
import es.deusto.bilboHotels.Main;

@ExtendWith(JUnitPerfTest.class)
public class DataInitializerIntegrationTest {

    private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    private static HttpServer server;
    private PersistenceManager pm;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        server = Main.startServer();
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        server.shutdown();
    }

    @BeforeEach
    public void setUp() throws Exception {
        pm = pmf.getPersistenceManager();
        pm.currentTransaction().begin();
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (pm.currentTransaction().isActive()) {
            pm.currentTransaction().rollback();
        }
        pm.close();
    }

    @Test
    public void testDataInitialization() {
        // Inicializar datos
        DataInitializer.initializeData(pm);

        // Comprobar que los datos se han insertado correctamente
        List<Usuario> usuarios = pm.newQuery(Usuario.class).executeList();
        List<Hotel> hoteles = pm.newQuery(Hotel.class).executeList();
        List<Habitacion> habitaciones = pm.newQuery(Habitacion.class).executeList();

        // Validar algunos datos
        assertFalse(usuarios.isEmpty(), "Los usuarios no se han inicializado correctamente");
        assertFalse(hoteles.isEmpty(), "Los hoteles no se han inicializado correctamente");
        assertFalse(habitaciones.isEmpty(), "Las habitaciones no se han inicializado correctamente");

        // Ejemplos específicos de validación
        assertTrue(usuarios.stream().anyMatch(u -> u.getNombre().equals("admin")), "El usuario admin no se ha inicializado");
        assertTrue(hoteles.stream().anyMatch(h -> h.getNombre().equals("Hotel Central")), "El hotel central no se ha inicializado");
        assertTrue(habitaciones.stream().anyMatch(h -> h.getNumero() == 101), "La habitación 101 no se ha inicializado");
    }
}*/