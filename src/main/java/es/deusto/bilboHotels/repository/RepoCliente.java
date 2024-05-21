package es.deusto.bilboHotels.repository;

import es.deusto.bilboHotels.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoCliente extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByUsuarioId(Long usuarioId);

    @Query("SELECT c FROM Cliente c WHERE c.usuario.username = :username")
    Optional<Cliente> findByUsername(@Param("username") String username);

}
