package es.deusto.bilboHotels.model.dto;

import es.deusto.bilboHotels.model.enums.EstadoPago;
import es.deusto.bilboHotels.model.enums.MetodoPago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReservaDTOTest {

    private ReservaDTO reservaDTO1;
    private ReservaDTO reservaDTO2;

    @BeforeEach
    public void setUp() {
        reservaDTO1 = new ReservaDTO(1L, "ABC123", LocalDateTime.now(), 2L, 3L, LocalDate.now(),
                LocalDate.now().plusDays(1), new ArrayList<>(), BigDecimal.valueOf(100), "Hotel ABC",
                DireccionDTO.builder().lineaDireccion("Calle Falsa 123").ciudad("Bilbao").pais("España").build(),
                "John Doe", "john.doe@example.com", EstadoPago.PENDIENTE, MetodoPago.TARJETA_CREDITO);

        reservaDTO2 = new ReservaDTO(1L, "ABC123", LocalDateTime.now(), 2L, 3L, LocalDate.now(),
                LocalDate.now().plusDays(1), new ArrayList<>(), BigDecimal.valueOf(100), "Hotel ABC",
                DireccionDTO.builder().lineaDireccion("Calle Falsa 123").ciudad("Bilbao").pais("España").build(),
                "John Doe", "john.doe@example.com", EstadoPago.PENDIENTE, MetodoPago.TARJETA_CREDITO);
    }

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        String numeroConfirmacion = "ABC123";
        LocalDateTime fechaReserva = LocalDateTime.now();
        Long clienteId = 2L;
        Long hotelId = 3L;
        LocalDate fechaCheckIn = LocalDate.now();
        LocalDate fechaCheckOut = LocalDate.now().plusDays(1);
        List<HabitacionSeleccionDTO> seleccionesHabitacion = new ArrayList<>();
        BigDecimal precioTotal = BigDecimal.valueOf(100);
        String nombreHotel = "Hotel ABC";
        DireccionDTO direccionHotel = DireccionDTO.builder().lineaDireccion("Calle Falsa 123").ciudad("Bilbao").pais("España").build();
        String nombreCliente = "John Doe";
        String emailCliente = "john.doe@example.com";
        EstadoPago estadoPago = EstadoPago.PENDIENTE;
        MetodoPago metodoPago = MetodoPago.TARJETA_CREDITO;

        // Act
        ReservaDTO reservaDTO = new ReservaDTO(id, numeroConfirmacion, fechaReserva, clienteId, hotelId, fechaCheckIn,
                fechaCheckOut, seleccionesHabitacion, precioTotal, nombreHotel, direccionHotel, nombreCliente,
                emailCliente, estadoPago, metodoPago);

        // Assert
        assertNotNull(reservaDTO);
        assertEquals(id, reservaDTO.getId());
        assertEquals(numeroConfirmacion, reservaDTO.getNumeroConfirmacion());
        assertEquals(fechaReserva, reservaDTO.getFechaReserva());
        assertEquals(clienteId, reservaDTO.getClienteId());
        assertEquals(hotelId, reservaDTO.getHotelId());
        assertEquals(fechaCheckIn, reservaDTO.getFechaCheckIn());
        assertEquals(fechaCheckOut, reservaDTO.getFechaCheckOut());
        assertEquals(seleccionesHabitacion, reservaDTO.getSeleccionesHabitacion());
        assertEquals(precioTotal, reservaDTO.getPrecioTotal());
        assertEquals(nombreHotel, reservaDTO.getNombreHotel());
        assertEquals(direccionHotel, reservaDTO.getDireccionHotel());
        assertEquals(nombreCliente, reservaDTO.getNombreCliente());
        assertEquals(emailCliente, reservaDTO.getEmailCliente());
        assertEquals(estadoPago, reservaDTO.getEstadoPago());
        assertEquals(metodoPago, reservaDTO.getMetodoPago());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Act & Assert
        assertThat(reservaDTO1).isEqualTo(reservaDTO2);
        assertThat(reservaDTO1.hashCode()).isEqualTo(reservaDTO2.hashCode());

        reservaDTO2.setId(2L);

        assertThat(reservaDTO1).isNotEqualTo(reservaDTO2);
        assertThat(reservaDTO1.hashCode()).isNotEqualTo(reservaDTO2.hashCode());
    }

    @Test
    public void testBuilder() {
        // Act
        ReservaDTO reservaDTO = ReservaDTO.builder()
                .id(1L)
                .numeroConfirmacion("ABC123")
                .fechaReserva(LocalDateTime.now())
                .clienteId(2L)
                .hotelId(3L)
                .fechaCheckIn(LocalDate.now())
                .fechaCheckOut(LocalDate.now().plusDays(1))
                .seleccionesHabitacion(new ArrayList<>())
                .precioTotal(BigDecimal.valueOf(100))
                .nombreHotel("Hotel ABC")
                .direccionHotel(DireccionDTO.builder().lineaDireccion("Calle Falsa 123").ciudad("Bilbao").pais("España").build())
                .nombreCliente("John Doe")
                .emailCliente("john.doe@example.com")
                .estadoPago(EstadoPago.PENDIENTE)
                .metodoPago(MetodoPago.TARJETA_CREDITO)
                .build();

        // Assert
        assertNotNull(reservaDTO);
        assertEquals(1L, reservaDTO.getId());
        assertEquals("ABC123", reservaDTO.getNumeroConfirmacion());
    }

    @Test
    public void testSetters() {
        // Arrange
        ReservaDTO reservaDTO = new ReservaDTO();

        // Act
        reservaDTO.setId(1L);
        reservaDTO.setNumeroConfirmacion("ABC123");
        reservaDTO.setFechaReserva(LocalDateTime.now());
        reservaDTO.setClienteId(2L);
        reservaDTO.setHotelId(3L);
        reservaDTO.setFechaCheckIn(LocalDate.now());
        reservaDTO.setFechaCheckOut(LocalDate.now().plusDays(1));
        reservaDTO.setSeleccionesHabitacion(new ArrayList<>());
        reservaDTO.setPrecioTotal(BigDecimal.valueOf(100));
        reservaDTO.setNombreHotel("Hotel ABC");
        reservaDTO.setDireccionHotel(DireccionDTO.builder().lineaDireccion("Calle Falsa 123").ciudad("Bilbao").pais("España").build());
        reservaDTO.setNombreCliente("John Doe");
        reservaDTO.setEmailCliente("john.doe@example.com");
        reservaDTO.setEstadoPago(EstadoPago.PENDIENTE);
        reservaDTO.setMetodoPago(MetodoPago.TARJETA_CREDITO);

        // Assert
        assertEquals(1L, reservaDTO.getId());
        assertEquals("ABC123", reservaDTO.getNumeroConfirmacion());
        assertNotNull(reservaDTO.getFechaReserva());
        assertEquals(2L, reservaDTO.getClienteId());
        assertEquals(3L, reservaDTO.getHotelId());
        assertNotNull(reservaDTO.getFechaCheckIn());
        assertNotNull(reservaDTO.getFechaCheckOut());
        assertNotNull(reservaDTO.getSeleccionesHabitacion());
        assertEquals(BigDecimal.valueOf(100), reservaDTO.getPrecioTotal());
        assertEquals("Hotel ABC", reservaDTO.getNombreHotel());
        assertNotNull(reservaDTO.getDireccionHotel());
        assertEquals("John Doe", reservaDTO.getNombreCliente());
        assertEquals("john.doe@example.com", reservaDTO.getEmailCliente());
        assertEquals(EstadoPago.PENDIENTE, reservaDTO.getEstadoPago());
        assertEquals(MetodoPago.TARJETA_CREDITO, reservaDTO.getMetodoPago());
    }
}
