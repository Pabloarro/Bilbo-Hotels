package es.deusto.bilboHotels.service.impl;

import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.enums.TipoHabitacion;
import es.deusto.bilboHotels.model.dto.DireccionDTO;
import es.deusto.bilboHotels.model.dto.HotelDisponibilidadDTO;
import es.deusto.bilboHotels.model.dto.HabitacionDTO;
import es.deusto.bilboHotels.repository.RepoHotel;
import es.deusto.bilboHotels.service.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioBusquedaHotelImpl implements ServicioBusquedaHotel {

    private final RepoHotel repoHotel;
    private final ServicioDireccion servicioDireccion;
    private final ServicioHabitacion servicioHabitacion;
    private final ServicioDisponibilidad servicioDisponibilidad;

    @Override
    public List<HotelDisponibilidadDTO> buscarHotelesDisponiblesByCiudadAndFecha(String ciudad, LocalDate fechaCheckIn, LocalDate fechaCheckOut) {
        validateCheckinAndfechaCheckOuts(fechaCheckIn, fechaCheckOut);

        log.info("Intentando encontrar hoteles en {} con habitaciones disponibles desde {} hasta {}", ciudad, fechaCheckIn, fechaCheckOut);

        // Number of days between check-in and check-out
        Long numeroDias = ChronoUnit.DAYS.between(fechaCheckIn, fechaCheckOut);

        // 1. Fetch hotels that satisfy the criteria (min 1 available room throughout the booking range)
        List<Hotel> hotelsWithAvailableRooms = repoHotel.findHotelsWithAvailableRooms(ciudad, fechaCheckIn, fechaCheckOut, numeroDias);

        // 2. Fetch hotels that don't have any availability records for the entire booking range
        List<Hotel> hotelsWithoutAvailabilityRecords = repoHotel.findHotelsWithoutAvailabilityRecords(ciudad, fechaCheckIn, fechaCheckOut);

        // 3. Fetch hotels with partial availability; some days with records meeting the criteria and some days without any records
        List<Hotel> hotelsWithPartialAvailabilityRecords = repoHotel.findHotelsWithPartialAvailabilityRecords(ciudad, fechaCheckIn, fechaCheckOut, numeroDias);

        // Combine and deduplicate the hotels using a Set
        Set<Hotel> combinedHotels = new HashSet<>(hotelsWithAvailableRooms);
        combinedHotels.addAll(hotelsWithoutAvailabilityRecords);
        combinedHotels.addAll(hotelsWithPartialAvailabilityRecords);

        log.info("Se encontraron {} hoteles con habitaciones disponibles.", combinedHotels.size());

        // Convert the combined hotel list to DTOs for the response
        return combinedHotels.stream()
                .map(hotel -> mapHotelToHotelDisponibleDto(hotel, fechaCheckIn, fechaCheckOut))
                .collect(Collectors.toList());
    }

    @Override
    public HotelDisponibilidadDTO buscarHotelDisponibleById(Long hotelId, LocalDate fechaCheckIn, LocalDate fechaCheckOut) {
        validateCheckinAndfechaCheckOuts(fechaCheckIn, fechaCheckOut);

        log.info("Intentando encontrar el hotel con ID {} con habitaciones disponibles desde {} hasta {}", hotelId, fechaCheckIn, fechaCheckOut);

        Optional<Hotel> hotelOptional = repoHotel.findById(hotelId);
        if (hotelOptional.isEmpty()) {
            log.error( "No se encontró ningún hotel con ID: {}", hotelId);
            throw new EntityNotFoundException("Hotel no encontrado");
        }

        Hotel hotel = hotelOptional.get();
        return mapHotelToHotelDisponibleDto(hotel, fechaCheckIn, fechaCheckOut);
    }


    @Override
    public HotelDisponibilidadDTO mapHotelToHotelDisponibleDto(Hotel hotel, LocalDate fechaCheckIn, LocalDate fechaCheckOut) {
        List<HabitacionDTO> habitacionDTOs = hotel.getHabitaciones().stream()
                .map(servicioHabitacion::mapHabitacionToHabitacionDto)  // convert each Room to RoomDTO
                .collect(Collectors.toList());

        DireccionDTO direccionDTO = servicioDireccion.mapDireccionToDireccionDto(hotel.getDireccion());
        
        HotelDisponibilidadDTO hotelDisponibilidadDTO = HotelDisponibilidadDTO.builder()
                .id(hotel.getId())
                .nombre(hotel.getNombre())
                .direccionDTO(direccionDTO)
                .habitacionDTOs(habitacionDTOs)
                .build();
        
        // For each room type, find the minimum available rooms across the date range
        int maxAvailableSingleRooms = hotel.getHabitaciones().stream()
                .filter(habitacion -> habitacion.getTipoHabitacion() == TipoHabitacion.INDIVIDUAL)
                .mapToInt(habitacion -> servicioDisponibilidad.getMininimoHabitacionesDisponibles(habitacion.getId(), fechaCheckIn, fechaCheckOut))
                .max()
                .orElse(0); // Assume no single rooms if none match the filter
        hotelDisponibilidadDTO.setMaxHabitacionesIndividualDisponibles(maxAvailableSingleRooms);

        int maxAvailableDoubleRooms = hotel.getHabitaciones().stream()
                .filter(habitacion -> habitacion.getTipoHabitacion() == TipoHabitacion.DOBLE)
                .mapToInt(habitacion -> servicioDisponibilidad.getMininimoHabitacionesDisponibles(habitacion.getId(), fechaCheckIn, fechaCheckOut))
                .max()
                .orElse(0); // Assume no double rooms if none match the filter
        hotelDisponibilidadDTO.setMaxHabitacionesDoblesDisponibles(maxAvailableDoubleRooms);

        return hotelDisponibilidadDTO;
    }

    private void validateCheckinAndfechaCheckOuts(LocalDate fechaCheckIn, LocalDate fechaCheckOut) {
        if (fechaCheckIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de entrada no puede estar en el pasado");
        }
        if (fechaCheckOut.isBefore(fechaCheckIn.plusDays(1))) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.");
        }
    }

}