package es.deusto.bilboHotels.service.impl;

import es.deusto.bilboHotels.model.Cliente;
import es.deusto.bilboHotels.repository.RepoCliente;
import es.deusto.bilboHotels.service.ServicioCliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioClienteImpl implements ServicioCliente {

    private final RepoCliente repoCliente;

    @Override
    public Optional<Cliente> buscarByUsuarioId(Long usuarioId) {
        return repoCliente.findByUsuarioId(usuarioId);
    }

    @Override
    public Optional<Cliente> buscarByUsername(String username) {
        return repoCliente.findByUsername(username);
    }

}
