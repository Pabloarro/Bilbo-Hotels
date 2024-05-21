package es.deusto.bilboHotels.repository;

import es.deusto.bilboHotels.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoDireccion extends JpaRepository<Direccion, Long> {
}
