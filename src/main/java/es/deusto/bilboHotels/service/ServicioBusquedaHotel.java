package es.deusto.bilboHotels.service;

import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.dto.HotelDisponibilidadDTO;

import java.time.LocalDate;
import java.util.List;

public interface ServicioBusquedaHotel {

    List<HotelDisponibilidadDTO> buscarHotelesDisponiblesByCiudadAndFecha(String ciudad, LocalDate fechaCheckIn, LocalDate fechaCheckOut);

    HotelDisponibilidadDTO buscarHotelDisponibleById(Long hotelId, LocalDate fechaCheckIn, LocalDate fechaCheckOut);

    HotelDisponibilidadDTO mapHotelToHotelDisponibleDto(Hotel hotel, LocalDate fechaCheckIn, LocalDate fechaCheckOut);
}
