package es.deusto.bilboHotels.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import es.deusto.bilboHotels.model.dto.ReservaDTO;
import es.deusto.bilboHotels.service.ServicioReserva;
import es.deusto.bilboHotels.service.ServicioUsuario;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest implements IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioUsuario servicioUsuario;

    @MockBean
    private ServicioReserva servicioReserva;

    private ReservaDTO reservaDTO;

    @BeforeEach
    public void setup() {
        reservaDTO = ReservaDTO.builder()
                .id(1L)
                .numeroConfirmacion("123456")
                .fechaReserva(LocalDateTime.now())
                .clienteId(1L)
                .hotelId(1L)
                .fechaCheckIn(LocalDate.of(2024, 7, 1))
                .fechaCheckOut(LocalDate.of(2024, 7, 10))
                .precioTotal(BigDecimal.valueOf(1000))
                .nombreHotel("Hotel Bilbo")
                .nombreCliente("John Doe")
                .emailCliente("john.doe@example.com")
                .build();
    }
/*
    @Test
    @WithMockUser(username = "john.doe")
    public void testListBookings() throws Exception {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setId(1L);
        mockUsuario.setUsername("john.doe");

        Mockito.when(servicioUsuario.buscarUsuarioByUsername("john.doe")).thenReturn(mockUsuario);
        Mockito.when(servicioReserva.buscarReservasByClienteId(1L)).thenReturn(Collections.singletonList(reservaDTO));

        mockMvc.perform(get("/customer/bookings"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/bookings"))
                .andExpect(model().attributeExists("reservas"))
                .andExpect(model().attribute("reservas", hasSize(1)))
                .andExpect(model().attribute("reservas", hasItem(
                        allOf(
                                hasProperty("id", is(reservaDTO.getId())),
                                hasProperty("nombreCliente", is(reservaDTO.getNombreCliente()))
                        )
                )));
    }

    @Test
    @WithMockUser(username = "john.doe")
    public void testViewBookingDetails() throws Exception {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setId(1L);
        mockUsuario.setUsername("john.doe");

        Mockito.when(servicioUsuario.buscarUsuarioByUsername("john.doe")).thenReturn(mockUsuario);
        Mockito.when(servicioReserva.BuscarReservasByIdAndClienteId(1L, 1L)).thenReturn(reservaDTO);

        mockMvc.perform(get("/customer/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/bookings-details"))
                .andExpect(model().attributeExists("reservaDTO"))
                .andExpect(model().attribute("reservaDTO", hasProperty("id", is(reservaDTO.getId()))))
                .andExpect(model().attribute("dias", is(ChronoUnit.DAYS.between(reservaDTO.getFechaCheckIn(), reservaDTO.getFechaCheckOut()))));
    }*/
}
