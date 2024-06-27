package es.deusto.bilboHotels.model;

import es.deusto.bilboHotels.model.*;
import es.deusto.bilboHotels.model.dto.InicioReservaDTO;
import es.deusto.bilboHotels.model.enums.Divisa;
import es.deusto.bilboHotels.model.enums.EstadoPago;
import es.deusto.bilboHotels.model.enums.MetodoPago;
import es.deusto.bilboHotels.model.enums.TipoHabitacion;
import es.deusto.bilboHotels.model.enums.TipoRol;
import es.deusto.bilboHotels.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservaIntegrationTest {
    private ServicioReserva servicioReserva;

    private ServicioHotel servicioHotel;

    private ServicioHabitacion servicioHabitacion;

    private ServicioCliente servicioCliente;

    private ServicioPago servicioPago;

    private ServicioDisponibilidad servicioDisponibilidad;

    private ReservaIntegrationTest reservaIntegrationTest;

    private Hotel hotel;
    private Habitacion habitacion;
    private Cliente cliente;
    private InicioReservaDTO inicioReservaDTO;
    private Reserva reserva;

    @BeforeEach
    void setUp() {

        Rol rolManager = new Rol(TipoRol.HOTEL_MANAGER);
        Usuario managerUsuario = new Usuario(1L, "managerUser", "password", LocalDateTime.now(), "manager@example.com", "12345678", rolManager, null, null, null);
        HotelManager hotelManager = new HotelManager(1L, managerUsuario, null);

   hotel = Hotel.builder()
        .id(1L)
        .nombre("Hotel Test")
        .direccion(Direccion.builder()
                .lineaDireccion("Calle Test 123")
                .ciudad("Ciudad Test")
                .pais("Pais Test")
                .build())
        .hotelManager(hotelManager)
        .build();


        habitacion = Habitacion.builder()
                .id(1L)
                .hotel(hotel)
                .tipoHabitacion(TipoHabitacion.INDIVIDUAL)
                .contadorHabitacion(5)
                .precioPorNoche(BigDecimal.valueOf(100.00).doubleValue())
                .build();

        Rol rolCliente = new Rol(TipoRol.CLIENTE);
        Usuario clienteUsuario = new Usuario(2L, "testuser", "password", LocalDateTime.now(), "test@example.com", "87654321", rolCliente, null, null, null);

        cliente = Cliente.builder()
                .id(1L)
                .usuario(clienteUsuario)
                .build();

        inicioReservaDTO = new InicioReservaDTO(
                1L,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(5),
                1L,
                null, // Lista de habitaciones seleccionadas, si es necesario
                BigDecimal.valueOf(400.00)
        );

        reserva = Reserva.builder()
                .id(1L)
                .cliente(cliente)
                .hotel(hotel)
                .fechaCheckIn(LocalDate.now().plusDays(1))
                .fechaCheckOut(LocalDate.now().plusDays(5))
                .numeroConfirmacion("12345678")
                .build();
    }

    /*@Test
    void testCrearReserva() {
        when(servicioCliente.buscarByUsuarioId(any(Long.class))).thenReturn(Optional.of(cliente));
        when(servicioHotel.buscarHotelById(any(Long.class))).thenReturn(Optional.of(hotel));
        when(servicioHabitacion.buscarHabitacionbyId(any(Long.class))).thenReturn(Optional.of(habitacion));
        when(servicioReserva.saveBooking(any(InicioReservaDTO.class), any(Long.class))).thenReturn(reserva);
        when(servicioPago.savePayment(any(InicioReservaDTO.class), any(Reserva.class))).thenReturn(new Pago(
                1L,
                "12345",
                LocalDateTime.now(),
                reserva,
                BigDecimal.valueOf(400.00),
                EstadoPago.COMPLETADO,
                MetodoPago.TARJETA_CREDITO,
                Divisa.EUR
        ));
        when(servicioDisponibilidad.getMininimoHabitacionesDisponibles(any(Long.class), any(LocalDate.class), any(LocalDate.class))).thenReturn(1);

        Reserva createdReserva = servicioReserva.saveBooking(inicioReservaDTO, cliente.getId());

        assertNotNull(createdReserva);
        assertEquals(cliente, createdReserva.getCliente());
        assertEquals(hotel, createdReserva.getHotel());
        assertEquals(inicioReservaDTO.getFechaCheckIn(), createdReserva.getFechaCheckIn());
        assertEquals(inicioReservaDTO.getFechaCheckOut(), createdReserva.getFechaCheckOut());
        assertEquals("12345678", createdReserva.getNumeroConfirmacion());

        verify(servicioCliente, times(1)).buscarByUsuarioId(any(Long.class));
        verify(servicioHotel, times(1)).buscarHotelById(any(Long.class));
        verify(servicioHabitacion, times(1)).buscarHabitacionbyId(any(Long.class));
        verify(servicioReserva, times(1)).saveBooking(any(InicioReservaDTO.class), any(Long.class));
        verify(servicioPago, times(1)).savePayment(any(InicioReservaDTO.class), any(Reserva.class));
        verify(servicioDisponibilidad, times(1)).getMininimoHabitacionesDisponibles(any(Long.class), any(LocalDate.class), any(LocalDate.class));
    }*/
}

