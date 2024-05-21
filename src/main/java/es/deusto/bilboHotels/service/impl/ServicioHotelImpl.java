package es.deusto.bilboHotels.service.impl;

import es.deusto.bilboHotels.exception.HotelYaExisteException;
import es.deusto.bilboHotels.model.*;
import es.deusto.bilboHotels.model.dto.*;
import es.deusto.bilboHotels.repository.RepoHotel;
import es.deusto.bilboHotels.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioHotelImpl implements ServicioHotel {

    private final RepoHotel repoHotel;
    private final ServicioDireccion servicioDireccion;
    private final ServicioHabitacion servicioHabitacion;
    private final ServicioUsuario servicioUsuario;
    private final ServicioHotelManager servicioHotelManager;

    @Override
    @Transactional
    public Hotel guardarHotel(HotelRegistroDTO hotelRegistroDTO) {
        log.info("Intentando guardar un nuevo hotel: {}", hotelRegistroDTO.toString());

        Optional<Hotel> hotelExistente = repoHotel.findByNombre(hotelRegistroDTO.getNombre());
        if (hotelExistente.isPresent()) {
            throw new HotelYaExisteException("¡Este nombre de hotel ya está registrado!");
        }

        Hotel hotel = mapHotelRegistrationDtoToHotel(hotelRegistroDTO);

        Direccion direccionGuardada = servicioDireccion.guardarDireccion(hotelRegistroDTO.getDireccionDTO());
        hotel.setDireccion(direccionGuardada);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HotelManager hotelManager = servicioHotelManager.findByUsuario(servicioUsuario.buscarUsuarioByUsername(username));
        hotel.setHotelManager(hotelManager);

        hotel = repoHotel.save(hotel);

        List<Habitacion> habitacionesGuardadas = servicioHabitacion.guardarHabitaciones(hotelRegistroDTO.getHabitacionDTOs(), hotel);
        hotel.setHabitaciones(habitacionesGuardadas);

        Hotel hotelGuardado = repoHotel.save(hotel);
        log.info("Se ha guardado correctamente el nuevo hotel con ID: {}", hotel.getId());
        return hotelGuardado;
    }

    @Override
    public HotelDTO buscarHotelDtoByNombre(String nombre) {
        Hotel hotel = repoHotel.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));
        return mapHotelToHotelDto(hotel);
    }

    @Override
    public HotelDTO buscarHotelDtoById(Long id) {
        Hotel hotel = repoHotel.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));
        return mapHotelToHotelDto(hotel);
    }

    @Override
    public Optional<Hotel> buscarHotelById(Long id) {
        return repoHotel.findById(id);
    }

    @Override
    public List<HotelDTO> buscarAllHoteles() {
        List<Hotel> hotels = repoHotel.findAll();
        return hotels.stream()
                .map(this::mapHotelToHotelDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HotelDTO actualizarHotel(HotelDTO hotelDTO) {
        log.info("Intentando actualizar el hotel con ID: {}", hotelDTO.getId());

        Hotel hotelExistente = repoHotel.findById(hotelDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        if (hotelNameExistsAndNotSameHotel(hotelDTO.getNombre(), hotelDTO.getId())) {
            throw new HotelYaExisteException("Este nombre de hotel ya está registrado.");
        }

        hotelExistente.setNombre(hotelDTO.getNombre());

        Direccion direccionActualizada = servicioDireccion.actualizarDireccion(hotelDTO.getDireccionDTO());
        hotelExistente.setDireccion(direccionActualizada);

        hotelDTO.getHabitacionDTOs().forEach(servicioHabitacion::actualizarHabitacion);

        repoHotel.save(hotelExistente);
        log.info("Se ha actualizado correctamente el hotel existente con ID: {}", hotelDTO.getId());
        return mapHotelToHotelDto(hotelExistente);
    }

    @Override
    public void borrarHotelById(Long id) {
        log.info("Intentando eliminar el hotel con ID: {}", id);
        repoHotel.deleteById(id);
        log.info("Se ha eliminado correctamente el hotel con ID: {}", id);
    }
    @Override
    public List<Hotel> buscarTodosHotelesByManagerId(Long managerId) {
        List<Hotel> hotels = repoHotel.findAllByHotelManager_Id(managerId);
        return (hotels != null) ? hotels : Collections.emptyList();
    }

    @Override
    public List<HotelDTO> buscarTodosHotelDtosByManagerId(Long managerId) {
        List<Hotel> hotels = repoHotel.findAllByHotelManager_Id(managerId);
        if (hotels != null) {
            return hotels.stream()
                    .map(this::mapHotelToHotelDto)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public HotelDTO buscarHotelByIdAndManagerId(Long hotelId, Long managerId) {
        Hotel hotel = repoHotel.findByIdAndHotelManager_Id(hotelId, managerId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));
        return mapHotelToHotelDto(hotel);
    }

    @Override
    @Transactional
    public HotelDTO actualizarHotelByManagerId(HotelDTO hotelDTO, Long managerId) {
        log.info("Intentando actualizar el hotel con ID: {} para el ID del manager: {}", hotelDTO.getId(), managerId);

        Hotel hotelExistente = repoHotel.findByIdAndHotelManager_Id(hotelDTO.getId(), managerId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        if (hotelNameExistsAndNotSameHotel(hotelDTO.getNombre(), hotelDTO.getId())) {
            throw new HotelYaExisteException("El nombre de este hotel ya esta registrado!");
        }

        hotelExistente.setNombre(hotelDTO.getNombre());

        Direccion direccionActualizada = servicioDireccion.actualizarDireccion(hotelDTO.getDireccionDTO());
        hotelExistente.setDireccion(direccionActualizada);

        hotelDTO.getHabitacionDTOs().forEach(servicioHabitacion::actualizarHabitacion);

        repoHotel.save(hotelExistente);
        log.info("Se ha actualizado correctamente el hotel existente con ID: {} para el ID del gerente: {}", hotelDTO.getId(), managerId);
        return mapHotelToHotelDto(hotelExistente);    }

    @Override
    public void borrarHotelByIdAndManagerId(Long hotelId, Long managerId) {
        log.info("Intentando eliminar el hotel con ID: {} para el ID del gerente: {}", hotelId, managerId);
        Hotel hotel = repoHotel.findByIdAndHotelManager_Id(hotelId, managerId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));
            repoHotel.delete(hotel);
        log.info("Se ha eliminado correctamente el hotel con ID: {} para el ID del gerente: {}", hotelId, managerId);
    }

    private Hotel mapHotelRegistrationDtoToHotel(HotelRegistroDTO dto) {
        return Hotel.builder()
                .nombre(formatText(dto.getNombre()))
                .build();
    }

    @Override
    public HotelDTO mapHotelToHotelDto(Hotel hotel) {
        List<HabitacionDTO> habitacionDTOs = hotel.getHabitaciones().stream()
                .map(servicioHabitacion::mapHabitacionToHabitacionDto)  // convert each Room to RoomDTO
                .collect(Collectors.toList());  // collect results to a list

        DireccionDTO direccionDTO = servicioDireccion.mapDireccionToDireccionDto(hotel.getDireccion());

        return HotelDTO.builder()
                .id(hotel.getId())
                .nombre(hotel.getNombre())
                .direccionDTO(direccionDTO)
                .habitacionDTOs(habitacionDTOs)
                .managerUsername(hotel.getHotelManager().getUsuario().getUsername())
                .build();
    }

    private boolean hotelNameExistsAndNotSameHotel(String nombre, Long hotelId) {
        Optional<Hotel> hotelExistenteConMismoNombre = repoHotel.findByNombre(nombre);
        return hotelExistenteConMismoNombre.isPresent() && !hotelExistenteConMismoNombre.get().getId().equals(hotelId);
    }

    private String formatText(String text) {
        return StringUtils.capitalize(text.trim());
    }

}

