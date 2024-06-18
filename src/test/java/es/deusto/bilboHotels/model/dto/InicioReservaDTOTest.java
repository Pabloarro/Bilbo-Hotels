package es.deusto.bilboHotels.model.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        InicioReservaDTO inicioReservaDTO1 = new InicioReservaDTO(123, LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 12), 2, new ArrayList<>(), BigDecimal.valueOf(200));
        InicioReservaDTO inicioReservaDTO2 = new InicioReservaDTO(123, LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 12), 2, new ArrayList<>(), BigDecimal.valueOf(200));

        // Act & Assert
        assertEquals(inicioReservaDTO1, inicioReservaDTO2);
        assertEquals(inicioReservaDTO1.hashCode(), inicioReservaDTO2.hashCode());

        inicioReservaDTO2.setHotelId(456);

        assertNotEquals(inicioReservaDTO1, inicioReservaDTO2);
        assertNotEquals(inicioReservaDTO1.hashCode(), inicioReservaDTO2.hashCode());
    }

    @Test
    public void testBuilder() {
        // Arrange
        long hotelId = 123;
        LocalDate fechaCheckIn = LocalDate.of(2024, 6, 10);
        LocalDate fechaCheckOut = LocalDate.of(2024, 6, 12);
        long duracionDias = 2;
        List<HabitacionSeleccionDTO> habitacionSelecciones = new ArrayList<>();
        BigDecimal precioTotal = BigDecimal.valueOf(200);

        // Act
        InicioReservaDTO inicioReservaDTO = InicioReservaDTO.builder()
                .hotelId(hotelId)
                .fechaCheckIn(fechaCheckIn)
                .fechaCheckOut(fechaCheckOut)
                .duracionDias(duracionDias)
                .habitacionSelecciones(habitacionSelecciones)
                .precioTotal(precioTotal)
                .build();

        // Assert
        assertNotNull(inicioReservaDTO);
        assertEquals(hotelId, inicioReservaDTO.getHotelId());
        assertEquals(fechaCheckIn, inicioReservaDTO.getFechaCheckIn());
        assertEquals(fechaCheckOut, inicioReservaDTO.getFechaCheckOut());
        assertEquals(duracionDias, inicioReservaDTO.getDuracionDias());
        assertEquals(habitacionSelecciones, inicioReservaDTO.getHabitacionSelecciones());
        assertEquals(precioTotal, inicioReservaDTO.getPrecioTotal());
    }

    @Test
    public void testToString() {
        // Arrange
        InicioReservaDTO inicioReservaDTO = new InicioReservaDTO(123, LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 12), 2, new ArrayList<>(), BigDecimal.valueOf(200));
        
        // Act
        String toString = inicioReservaDTO.toString();

        // Assert
        assertNotNull(toString);
        assertThat(toString).contains("hotelId=123", "fechaCheckIn=2024-06-10", "fechaCheckOut=2024-06-12", "duracionDias=2", "habitacionSelecciones=[]", "precioTotal=200");
    }

    @Test
    public void testSetters() {
        // Arrange
        InicioReservaDTO inicioReservaDTO = new InicioReservaDTO();

        // Act
        inicioReservaDTO.setHotelId(123);
        inicioReservaDTO.setFechaCheckIn(LocalDate.of(2024, 6, 10));
        inicioReservaDTO.setFechaCheckOut(LocalDate.of(2024, 6, 12));
        inicioReservaDTO.setDuracionDias(2);
        inicioReservaDTO.setHabitacionSelecciones(new ArrayList<>());
        inicioReservaDTO.setPrecioTotal(BigDecimal.valueOf(200));

        // Assert
        assertEquals(123, inicioReservaDTO.getHotelId());
        assertEquals(LocalDate.of(2024, 6, 10), inicioReservaDTO.getFechaCheckIn());
        assertEquals(LocalDate.of(2024, 6, 12), inicioReservaDTO.getFechaCheckOut());
        assertEquals(2, inicioReservaDTO.getDuracionDias());
        assertEquals(new ArrayList<>(), inicioReservaDTO.getHabitacionSelecciones());
        assertEquals(BigDecimal.valueOf(200), inicioReservaDTO.getPrecioTotal());
    }
}
