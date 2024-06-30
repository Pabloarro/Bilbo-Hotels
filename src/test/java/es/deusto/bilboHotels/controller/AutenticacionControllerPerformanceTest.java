package es.deusto.bilboHotels.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.deusto.bilboHotels.exception.NombreUsuarioYaExisteException;
import es.deusto.bilboHotels.model.Usuario;
import es.deusto.bilboHotels.model.dto.RegistroUsuarioDTO;
import es.deusto.bilboHotels.service.ServicioUsuario;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import org.perf4j.log4j.Log4JStopWatch;


@Category(PerformanceTest.class)
public class AutenticacionControllerPerformanceTest {

    private AutenticacionController autenticacionController;

    @Mock
    private ServicioUsuario servicioUsuario;

    @Mock
    private BindingResult bindingResult;
    
    @Rule
    JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/report.html"));

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        autenticacionController = new AutenticacionController(servicioUsuario);
    }

    @Test
    @PerformanceTest
    @JUnitPerfTest(threads = 2, durationMs = 1000)
    @JUnitPerfTestRequirement(meanLatency = 100)
    public void testRegisterCustomerAccountSuccess() throws Exception {
        // Prepare mock data
        RegistroUsuarioDTO registroDTO = RegistroUsuarioDTO.builder()
            .username("test-customer")
            .password("password")
            .nombre("Nombre")
            .apellido("Apellido")
            .build();
        
        when(bindingResult.hasErrors()).thenReturn(false);
        when(servicioUsuario.guardarUsuario(any(RegistroUsuarioDTO.class))).thenReturn(new Usuario());

        // Call tested method
        String viewName = autenticacionController.registerCustomerAccount(registroDTO, bindingResult);

        // Check expected response
        assertEquals("redirect:/register/customer?success", viewName);
    }

    @Test
    @PerformanceTest
    @JUnitPerfTest(threads = 2, durationMs = 1000)
    @JUnitPerfTestRequirement(meanLatency = 100)
    public void testRegisterCustomerAccountFailureDueToExistingUsername() throws Exception {
        // Prepare mock data
        RegistroUsuarioDTO registroDTO = RegistroUsuarioDTO.builder()
            .username("test-customer")
            .password("password")
            .nombre("Nombre")
            .apellido("Apellido")
            .build();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(servicioUsuario.guardarUsuario(any(RegistroUsuarioDTO.class))).thenThrow(new NombreUsuarioYaExisteException("Username already exists"));

        // Call tested method
        String viewName = autenticacionController.registerCustomerAccount(registroDTO, bindingResult);

        // Check expected response
        assertEquals("register-customer", viewName);
        verify(bindingResult).rejectValue("username", "user.exists", "Username already exists");
    }

    @Test
    @PerformanceTest
    @JUnitPerfTest(threads = 2, durationMs = 1000)
    @JUnitPerfTestRequirement(meanLatency = 100)
    public void testRegisterCustomerAccountFailureDueToValidationErrors() throws Exception {
        // Prepare mock data
        RegistroUsuarioDTO registroDTO = RegistroUsuarioDTO.builder()
            .username("test-customer")
            .password("password")
            .nombre("Nombre")
            .apellido("Apellido")
            .build();

        when(bindingResult.hasErrors()).thenReturn(true);

        // Call tested method
        String viewName = autenticacionController.registerCustomerAccount(registroDTO, bindingResult);

        // Check expected response
        assertEquals("register-customer", viewName);
    }



}
