package es.deusto.bilboHotels.service;

import es.deusto.bilboHotels.model.Cliente;

import java.util.Optional;

public interface ServicioCliente {

    Optional<Cliente> buscarByUsuarioId(Long usuarioId);

    Optional<Cliente> buscarByUsername(String username);
}
