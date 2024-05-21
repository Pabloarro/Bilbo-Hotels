package es.deusto.bilboHotels.service.impl;

import es.deusto.bilboHotels.model.Hotel;
import es.deusto.bilboHotels.model.Habitacion;
import es.deusto.bilboHotels.model.dto.HabitacionDTO;
import es.deusto.bilboHotels.repository.RepoHabitacion;
import es.deusto.bilboHotels.service.ServicioHabitacion;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioHabitacionImpl implements ServicioHabitacion {

    private final RepoHabitacion repoHabitacion;

    @Override
    public Habitacion guardarHabitacion(HabitacionDTO habitacionDTO, Hotel hotel) {
        log.info("Intentando guardar una nueva habitación: {}", habitacionDTO);
        Habitacion habitacion = mapHabitacionDtoToHabitacion(habitacionDTO, hotel);
        habitacion = repoHabitacion.save(habitacion);
        log.info("Se ha guardado correctamente la habitación con ID: {}", habitacion.getId());
        return habitacion;
    }

    @Override
    public List<Habitacion> guardarHabitaciones(List<HabitacionDTO> habitacionDTOs, Hotel hotel) {
        log.info("Intentando guardar habitaciones: {}", habitacionDTOs);
        List<Habitacion> habitaciones = habitacionDTOs.stream()
                .map(habitacionDTO -> guardarHabitacion(habitacionDTO, hotel)) // save each room
                .collect(Collectors.toList());
        log.info("Habitaciones guardadas correctamente: {}", habitaciones);
        return habitaciones;
    }

    @Override
    public Optional<Habitacion> buscarHabitacionbyId(Long id) {
        return repoHabitacion.findById(id);
    }

    @Override
    public List<Habitacion> buscarHabitacionesByHotelId(Long hotelId) {
        return null;
    }

    @Override
    public Habitacion actualizarHabitacion(HabitacionDTO habitacionDTO) {
        log.info("Intentando actualizar la habitación con ID: {}", habitacionDTO.getId());
        Habitacion habitacionExistente = repoHabitacion.findById(habitacionDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Habitacion no encontrada"));

        log.info("Se encontró la habitación con ID: {}", habitacionDTO.getId());

        habitacionExistente.setTipoHabitacion(habitacionDTO.getTipoHabitacion());
        habitacionExistente.setContadorHabitacion(habitacionDTO.getContadorHabitacion());
        habitacionExistente.setPrecioPorNoche(habitacionDTO.getPrecioPorNoche());

        Habitacion habitacionActualizada = repoHabitacion.save(habitacionExistente);
        log.info("Se ha actualizado correctamente la dirección con ID: {}", habitacionExistente.getId());
        return habitacionActualizada;
    }

    @Override
    public void borrarHabitacion(Long id) {

    }

    @Override
    public Habitacion mapHabitacionDtoToHabitacion(HabitacionDTO habitacionDTO, Hotel hotel) {
        log.debug("Mapeando HabitacionDTO a habitacion: {}", habitacionDTO);
        Habitacion habitacion = Habitacion.builder()
                .hotel(hotel)
                .tipoHabitacion(habitacionDTO.getTipoHabitacion())
                .contadorHabitacion(habitacionDTO.getContadorHabitacion())
                .precioPorNoche(habitacionDTO.getPrecioPorNoche())
                .build();
        log.debug("Habitacion mapeada: {}", habitacion);
        return habitacion;

    }

    @Override
    public HabitacionDTO mapHabitacionToHabitacionDto(Habitacion habitacion) {
        return HabitacionDTO.builder()
                .id(habitacion.getId())
                .hotelId(habitacion.getHotel().getId())
                .tipoHabitacion(habitacion.getTipoHabitacion())
                .contadorHabitacion(habitacion.getContadorHabitacion())
                .precioPorNoche(habitacion.getPrecioPorNoche())
                .build();
    }
}
