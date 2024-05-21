package es.deusto.bilboHotels.model.dto;

import es.deusto.bilboHotels.model.enums.TipoHabitacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionSeleccionDTO {

    private TipoHabitacion tipoHabitacion;
    private int contar;
}
