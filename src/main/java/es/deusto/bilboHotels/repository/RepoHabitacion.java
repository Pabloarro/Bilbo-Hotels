package es.deusto.bilboHotels.repository;

import es.deusto.bilboHotels.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoHabitacion extends JpaRepository<Habitacion, Long> {
}
