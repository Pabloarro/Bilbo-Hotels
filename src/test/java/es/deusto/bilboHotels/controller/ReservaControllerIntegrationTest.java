package es.deusto.bilboHotels.controller;

import es.deusto.bilboHotels.model.dto.*;
import es.deusto.bilboHotels.service.ServicioReserva;
import es.deusto.bilboHotels.service.ServicioHotel;
import es.deusto.bilboHotels.service.ServicioUsuario;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReservaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ServicioHotel servicioHotel;

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private ServicioReserva servicioReserva;

    private MockHttpSession session;

    @BeforeEach
    public void setup() {
        session = new MockHttpSession();
        InicioReservaDTO inicioReservaDTO = InicioReservaDTO.builder()
            .hotelId(1L)
            .fechaCheckIn(LocalDate.now().plusDays(1))
            .fechaCheckOut(LocalDate.now().plusDays(2))
            .habitacionSelecciones(new ArrayList<>())
            .precioTotal(null)
            .build();
        session.setAttribute("inicioReservaDTO", inicioReservaDTO);
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testInitiateBooking() throws Exception {
        mockMvc.perform(post("/booking/initiate")
            .session(session)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("hotelId", "1")
            .param("fechaCheckIn", LocalDate.now().plusDays(1).toString())
            .param("fechaCheckOut", LocalDate.now().plusDays(2).toString()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/booking/payment"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testMostrarPaginaPago() throws Exception {
        mockMvc.perform(get("/booking/payment")
            .session(session))
            .andExpect(status().isOk())
            .andExpect(view().name("booking/payment"))
            .andExpect(model().attributeExists("inicioReservaDTO"))
            .andExpect(model().attributeExists("hotelDTO"))
            .andExpect(model().attributeExists("tarjetaPagoDTO"));
    }

    /*@Test
@WithMockUser(username = "testuser", roles = "USER")
public void testConfirmBooking() throws Exception {
    mockMvc.perform(post("/booking/payment")
        .session(session)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("cardNumber", "4111111111111111")
        .param("expiryDate", "12/25")
        .param("cvv", "123"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/booking/confirmation"));
}

@Test
@WithMockUser(username = "testuser", roles = "USER")
public void testMostrarPaginaConfirmacion() throws Exception {
    ReservaDTO reservaDTO = ReservaDTO.builder()
        .hotelId(1L)
        .clienteId(1L)
        .fechaCheckIn(LocalDate.now().plusDays(1))
        .fechaCheckOut(LocalDate.now().plusDays(2))
        .seleccionesHabitacion(new ArrayList<>())
        .precioTotal(null)
        .build();

    mockMvc.perform(get("/booking/confirmation")
        .flashAttr("reservaDTO", reservaDTO))
        .andExpect(status().isOk())
        .andExpect(view().name("booking/confirmation"))
        .andExpect(model().attributeExists("dias"))
        .andExpect(model().attributeExists("reservaDTO"));
}*/

}
