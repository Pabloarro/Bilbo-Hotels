package es.deusto.bilboHotels.repository;

import es.deusto.bilboHotels.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoPago extends JpaRepository<Pago, Long> {
}
