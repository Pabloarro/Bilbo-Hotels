package es.deusto.bilboHotels.model.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InicioReservaDTOTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        long hotelId = 123;
        LocalDate fechaCheckIn = LocalDate.of(2024, 6, 10);
        LocalDate fechaCheckOut = LocalDate.of(2024, 6, 12);
        long duracionDias = 2;
        List<HabitacionSeleccionDTO> habitacionSelecciones = new ArrayList<>();
        BigDecimal precioTotal = BigDecimal.valueOf(200);

        // Act
        InicioReservaDTO inicioReservaDTO = new InicioReservaDTO(hotelId, fechaCheckIn, fechaCheckOut, duracionDias, habitacionSelecciones, precioTotal);

        // Assert
        assertNotNull(inicioReservaDTO);
        assertEquals(hotelId, inicioReservaDTO.getHotelId());
        assertEquals(fechaCheckIn, inicioReservaDTO.getFechaCheckIn());
        assertEquals(fechaCheckOut, inicioReservaDTO.getFechaCheckOut());
        assertEquals(duracionDias, inicioReservaDTO.getDuracionDias());
        assertEquals(habitacionSelecciones, inicioReservaDTO.getHabitacionSelecciones());
        assertEquals(precioTotal, inicioReservaDTO.getPrecioTotal());
    }
}
