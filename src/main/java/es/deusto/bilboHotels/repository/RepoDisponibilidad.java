package es.deusto.bilboHotels.repository;

import es.deusto.bilboHotels.model.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RepoDisponibilidad extends JpaRepository<Disponibilidad, Long> {

    // Find max amount of available rooms for the least available day throughout the booking range
    @Query("SELECT MIN(COALESCE(a.habitacionesDisponibles, r.contadorHabitacion)) FROM Habitacion r LEFT JOIN Disponibilidad a ON a.habitacion.id = r.id AND a.fecha BETWEEN :fechaCheckIn AND :fechaCheckOut WHERE r.id = :habitacionId")
    Optional<Integer> getMinAvailableRooms(@Param("habitacionId") Long habitacionId, @Param("fechaCheckIn") LocalDate fechaCheckIn, @Param("fechaCheckOut") LocalDate fechaCheckOut);

    Optional<Disponibilidad> findByHabitacionIdAndFecha(Long habitacionId, LocalDate fecha);

}
