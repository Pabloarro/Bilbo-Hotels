package es.deusto.bilboHotels.repository;

import es.deusto.bilboHotels.model.Rol;
import es.deusto.bilboHotels.model.enums.TipoRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoRol extends JpaRepository<Rol, Long> {

    Rol findByTipoRol(TipoRol tipoRol);
}
