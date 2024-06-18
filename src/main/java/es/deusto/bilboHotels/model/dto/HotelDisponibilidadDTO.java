package es.deusto.bilboHotels.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class HotelDisponibilidadDTO {

    private Long id;

    private String nombre;

    private DireccionDTO direccionDTO;

    private List<HabitacionDTO> habitacionDTOs = new ArrayList<>();

    private Integer maxHabitacionesIndividualDisponibles;

    private Integer maxHabitacionesDoblesDisponibles;

    @Override
public String toString() {
    return "HotelDisponibilidadDTO{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", direccionDTO=" + direccionDTO +
            ", habitacionDTOs=" + habitacionDTOs +
            ", maxHabitacionesIndividualDisponibles=" + maxHabitacionesIndividualDisponibles +
            ", maxHabitacionesDoblesDisponibles=" + maxHabitacionesDoblesDisponibles +
            '}';
}

}
