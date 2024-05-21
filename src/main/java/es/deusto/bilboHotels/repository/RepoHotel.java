package es.deusto.bilboHotels.repository;

import es.deusto.bilboHotels.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepoHotel extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByNombre(String nombre);

    List<Hotel> findAllByHotelManager_Id(Long id);

    Optional<Hotel> findByIdAndHotelManager_Id(Long id, Long managerId);

    @Query("SELECT h FROM Hotel h WHERE h.direccion.ciudad = :ciudad")
    List<Hotel> findHotelsByCity(@Param("ciudad") String ciudad);

    @Query("SELECT h " +
            "FROM Hotel h " +
            "JOIN h.habitaciones r " +
            "LEFT JOIN Disponibilidad a ON a.habitacion.id = r.id " +
            "AND a.fecha >= :fechaCheckIn AND a.fecha < :fechaCheckOut " +
            "WHERE h.direccion.ciudad = :ciudad " +
            "AND (a IS NULL OR a.habitacionesDisponibles > 0) " +
            "GROUP BY h.id, r.id " +
            "HAVING COUNT(DISTINCT a.fecha) + SUM(CASE WHEN a IS NULL THEN 1 ELSE 0 END) = :numberOfDays")
    List<Hotel> findHotelsWithAvailableRooms(@Param("ciudad") String city,
                                             @Param("fechaCheckIn") LocalDate fechaCheckIn,
                                             @Param("fechaCheckOut") LocalDate fechaCheckOut,
                                             @Param("numberOfDays") Long numberOfDays);

    @Query("SELECT h " +
            "FROM Hotel h " +
            "WHERE h.direccion.ciudad = :ciudad " +
            "AND NOT EXISTS (" +
            "   SELECT 1 " +
            "   FROM Disponibilidad a " +
            "   WHERE a.habitacion.hotel.id = h.id " +
            "   AND a.fecha >= :fechaCheckIn AND a.fecha < :fechaCheckOut" +
            ")")
    List<Hotel> findHotelsWithoutAvailabilityRecords(@Param("ciudad") String ciudad,
                                                     @Param("fechaCheckIn") LocalDate fechaCheckIn,
                                                     @Param("fechaCheckOut") LocalDate fechaCheckOut);

    @Query("SELECT h " +
            "FROM Hotel h " +
            "JOIN h.habitaciones r " +
            "LEFT JOIN Disponibilidad a ON r.id = a.habitacion.id " +
            "AND a.fecha >= :fechaCheckIn AND a.fecha < :fechaCheckOut " +
            "WHERE h.direccion.ciudad = :ciudad " +
            "AND (a IS NULL OR a.habitacionesDisponibles > 0) " +
            "GROUP BY h.id " +
            "HAVING COUNT(DISTINCT a.fecha) < :numberOfDays " +
            "AND COUNT(DISTINCT CASE WHEN a.habitacionesDisponibles > 0 THEN a.fecha END) > 0")
    List<Hotel> findHotelsWithPartialAvailabilityRecords(@Param("ciudad") String ciudad,
                                                         @Param("fechaCheckIn") LocalDate fechaCheckIn,
                                                         @Param("fechaCheckOut") LocalDate fechaCheckOut,
                                                         @Param("numberOfDays") Long numberOfDays);

}
