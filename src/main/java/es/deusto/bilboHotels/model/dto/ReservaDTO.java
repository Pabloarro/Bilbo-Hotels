package es.deusto.bilboHotels.model.dto;

import es.deusto.bilboHotels.model.enums.MetodoPago;
import es.deusto.bilboHotels.model.enums.EstadoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Long id;
    private String numeroConfirmacion;
    private LocalDateTime fechaReserva;
    private Long clienteId;
    private Long hotelId;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;
    private List<HabitacionSeleccionDTO> seleccionesHabitacion = new ArrayList<>();
    private BigDecimal precioTotal;
    private String nombreHotel;
    private DireccionDTO direccionHotel;
    private String nombreCliente;
    private String emailCliente;
    private EstadoPago estadoPago;
    private MetodoPago metodoPago;
    
}
