package es.deusto.bilboHotels.service;

import es.deusto.bilboHotels.model.Usuario;
import es.deusto.bilboHotels.model.dto.ResetearPasswordDTO;
import es.deusto.bilboHotels.model.dto.UsuarioDTO;
import es.deusto.bilboHotels.model.dto.RegistroUsuarioDTO;

import java.util.List;

public interface ServicioUsuario {

    Usuario guardarUsuario(RegistroUsuarioDTO registrationDTO);

    // For registration
    Usuario buscarUsuarioByUsername(String username);

    UsuarioDTO buscarUsuarioDTOByUsername(String username);

    UsuarioDTO buscarUsuarioById(Long id);

    List<UsuarioDTO> buscarAllUsuarios();

    void actualizarUsuario(UsuarioDTO usuarioDTO);

    void actualizarUsuarioLoggeado(UsuarioDTO userDTO);

    void borrarUsuarioById(Long id);

    Usuario resetPassword(ResetearPasswordDTO resetearPasswordDTO);

}
