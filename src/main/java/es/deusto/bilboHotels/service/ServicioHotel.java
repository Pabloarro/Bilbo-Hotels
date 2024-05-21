package es.deusto.bilboHotels.service;

import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.dto.HotelDTO;
import es.deusto.bilboHotels.model.dto.HotelRegistroDTO;

import java.util.List;
import java.util.Optional;

public interface ServicioHotel {

    Hotel guardarHotel(HotelRegistroDTO hotelRegistrationDTO);

    HotelDTO buscarHotelDtoByNombre(String nombre);

    HotelDTO buscarHotelDtoById(Long id);

    Optional<Hotel> buscarHotelById(Long id);

    List<HotelDTO> buscarAllHoteles();

    HotelDTO actualizarHotel(HotelDTO hotelDTO);

    void borrarHotelById(Long id);

    List<Hotel> buscarTodosHotelesByManagerId(Long managerId);

    List<HotelDTO> buscarTodosHotelDtosByManagerId(Long managerId);

    HotelDTO buscarHotelByIdAndManagerId(Long hotelId, Long managerId);

    HotelDTO actualizarHotelByManagerId(HotelDTO hotelDTO, Long managerId);

    void borrarHotelByIdAndManagerId(Long hotelId, Long managerId);

    HotelDTO mapHotelToHotelDto(Hotel hotel);

}
