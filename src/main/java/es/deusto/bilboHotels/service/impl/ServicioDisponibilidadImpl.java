package es.deusto.bilboHotels.service.impl;

import es.deusto.bilboHotels.model.Disponibilidad;
import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.Habitacion;
import es.deusto.bilboHotels.model.dto.HabitacionSeleccionDTO;
import es.deusto.bilboHotels.model.enums.TipoHabitacion;
import es.deusto.bilboHotels.repository.RepoDisponibilidad;
import es.deusto.bilboHotels.service.ServicioDisponibilidad;
import es.deusto.bilboHotels.service.ServicioHotel;
import es.deusto.bilboHotels.service.ServicioHabitacion;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioDisponibilidadImpl implements ServicioDisponibilidad {

    private final RepoDisponibilidad repoDisponibilidad;
    private final ServicioHotel servicioHotel;
    private final ServicioHabitacion servicioHabitacion;

    @Override
    public Integer getMininimoHabitacionesDisponibles(Long habitacionId, LocalDate fechaCheckIn, LocalDate fechaCheckOut) {
        log.info("Recuperando la cantidad mínima de habitaciones disponibles para el ID de habitación {} desde {} hasta {}", habitacionId, fechaCheckIn, fechaCheckOut);

        Habitacion habitacion = servicioHabitacion.buscarHabitacionbyId(habitacionId).orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada"));

        // Fetch the minimum available rooms throughout the booking range for a room ID.
        return repoDisponibilidad.getMinAvailableRooms(habitacionId, fechaCheckIn, fechaCheckOut)
                .orElse(habitacion.getContadorHabitacion()); // Consider no record as full availability
    }

    @Override
    public void actualizarDisponibilidades(long hotelId, LocalDate fechaCheckIn, LocalDate fechaCheckOut, List<HabitacionSeleccionDTO> seleccionHabitaciones) {
        Hotel hotel = servicioHotel.buscarHotelById(hotelId).orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));
        log.info("Intentando actualizar las disponibilidades para el ID del hotel {} desde {} hasta {}", hotelId, fechaCheckIn, fechaCheckOut);

        seleccionHabitaciones = seleccionHabitaciones.stream()
                .filter(seleccionHabitacion -> seleccionHabitacion.getContar() > 0)
                .collect(Collectors.toList());

        // Iterate through the room selections made by the user
        for (HabitacionSeleccionDTO seleccionHabitacion : seleccionHabitaciones) {
            TipoHabitacion tipoHabitacion = seleccionHabitacion.getTipoHabitacion();
            int selectedCount = seleccionHabitacion.getContar();
            log.debug("Procesando {} habitación(es) de tipo {}", selectedCount, tipoHabitacion);

            // Find the room by roomType for the given hotel
            Habitacion room = hotel.getHabitaciones().stream()
                    .filter(r -> r.getTipoHabitacion() == tipoHabitacion)
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Tipo habitacion no encontrada"));

            // Iterate through the dates and update or create availability
            for (LocalDate fecha = fechaCheckIn; fecha.isBefore(fechaCheckOut); fecha = fecha.plusDays(1)) {
                final LocalDate fechaActual = fecha; // Temporary final variable
                Disponibilidad disponibilidad = repoDisponibilidad.findByHabitacionIdAndFecha(room.getId(), fecha)
                        .orElseGet(() -> Disponibilidad.builder()
                                .hotel(hotel)
                                .fecha(fechaActual)
                                .habitacion(room)
                                .habitacionesDisponibles(room.getContadorHabitacion())
                                .build());

                // Reduce the available rooms by the selected count
                int actualizadasHabitacionesDisponibles = disponibilidad.getHabitacionesDisponibles() - selectedCount;
                if (actualizadasHabitacionesDisponibles < 0) {
                    throw new IllegalArgumentException("Las habitaciones seleccionadas superan las habitaciones disponibles para la fecha: " + fechaActual);
                }
                disponibilidad.setHabitacionesDisponibles(actualizadasHabitacionesDisponibles);

                repoDisponibilidad.save(disponibilidad);
            }
        }
        log.info("Se han actualizado las disponibilidades correctamente para el ID del hotel {} desde {} hasta {}", hotelId, fechaCheckIn, fechaCheckOut);
    }

}
