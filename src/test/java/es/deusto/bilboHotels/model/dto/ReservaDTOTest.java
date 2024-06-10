package es.deusto.bilboHotels.model.dto;

import es.deusto.bilboHotels.model.enums.EstadoPago;
import es.deusto.bilboHotels.model.enums.MetodoPago;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReservaDTOTest {

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
}
