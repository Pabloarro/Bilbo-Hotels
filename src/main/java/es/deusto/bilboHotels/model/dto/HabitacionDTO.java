package es.deusto.bilboHotels.model.dto;

import es.deusto.bilboHotels.model.enums.TipoHabitacion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionDTO {

    private Long id;

    private Long hotelId;

    private TipoHabitacion tipoHabitacion;

    @NotNull(message = "El contador de habitacion no puede estar vacio")
    @PositiveOrZero(message = "El contador de habitaciones debe ser 0 o mas")
    private Integer contadorHabitacion;

    @NotNull(message = "El preciop no puede estar vacio")
    @PositiveOrZero(message = "El precio por noche debe ser 0 o más.")
    private Double precioPorNoche;

}