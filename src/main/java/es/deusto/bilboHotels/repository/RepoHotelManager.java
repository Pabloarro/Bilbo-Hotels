package es.deusto.bilboHotels.repository;

import es.deusto.bilboHotels.model.HotelManager;
import es.deusto.bilboHotels.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoHotelManager extends JpaRepository<HotelManager, Long> {

    Optional<HotelManager> findByUsuario(Usuario usuario);
}
