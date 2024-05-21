package es.deusto.bilboHotels.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InicioReservaDTO {

    private long hotelId;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;
    private long duracionDias;
    private List<HabitacionSeleccionDTO> habitacionSelecciones = new ArrayList<>();
    private BigDecimal precioTotal;

}
