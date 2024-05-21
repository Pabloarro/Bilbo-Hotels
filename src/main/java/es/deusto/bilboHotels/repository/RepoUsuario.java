package es.deusto.bilboHotels.repository;

import es.deusto.bilboHotels.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoUsuario extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    boolean existsByUsername(String username);
}
