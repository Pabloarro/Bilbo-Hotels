package es.deusto.bilboHotels.service;

import es.deusto.bilboHotels.model.dto.HabitacionSeleccionDTO;

import java.time.LocalDate;
import java.util.List;

public interface ServicioDisponibilidad {

    Integer getMininimoHabitacionesDisponibles(Long habitacionId, LocalDate fechaCheckIn, LocalDate fechaCheckOut);

    void actualizarDisponibilidades(long hotelId, LocalDate fechaCheckIn, LocalDate fechaCheckOut, List<HabitacionSeleccionDTO> seleccionHabitaciones);

}
