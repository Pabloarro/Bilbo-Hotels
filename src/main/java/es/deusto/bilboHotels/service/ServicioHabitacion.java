package es.deusto.bilboHotels.service;

import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.Habitacion;
import es.deusto.bilboHotels.model.dto.HabitacionDTO;

import java.util.List;
import java.util.Optional;

public interface ServicioHabitacion {

    Habitacion guardarHabitacion(HabitacionDTO roomDTO, Hotel hotel);

    List<Habitacion> guardarHabitaciones(List<HabitacionDTO> roomDTOs, Hotel hotel);

    Optional<Habitacion> buscarHabitacionbyId(Long id);

    List<Habitacion> buscarHabitacionesByHotelId(Long hotelId);

    Habitacion actualizarHabitacion(HabitacionDTO roomDTO);

    void borrarHabitacion(Long id);

    Habitacion mapHabitacionDtoToHabitacion(HabitacionDTO roomDTO, Hotel hotel);

    HabitacionDTO mapHabitacionToHabitacionDto(Habitacion room);

}
