package es.deusto.bilboHotels.service.impl;

import es.deusto.bilboHotels.exception.NombreUsuarioYaExisteException;
import es.deusto.bilboHotels.model.*;
import es.deusto.bilboHotels.model.dto.ResetearPasswordDTO;
import es.deusto.bilboHotels.model.dto.UsuarioDTO;
import es.deusto.bilboHotels.model.dto.RegistroUsuarioDTO;
import es.deusto.bilboHotels.model.enums.TipoRol;
import es.deusto.bilboHotels.repository.RepoCliente;
import es.deusto.bilboHotels.repository.RepoHotelManager;
import es.deusto.bilboHotels.repository.RepoRol;
import es.deusto.bilboHotels.repository.RepoUsuario;
import es.deusto.bilboHotels.service.ServicioUsuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioUsuarioImpl implements ServicioUsuario {

    private final RepoUsuario repoUsuario;
    private final RepoRol repoRol;
    private final RepoCliente repoCliente;
    private final RepoHotelManager repoHotelManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Usuario guardarUsuario(RegistroUsuarioDTO registroDTO) {
        log.info("Intentando guardar un nuevo usuario: {}", registroDTO.getUsername());

        Optional<Usuario> usuarioExistente = Optional.ofNullable(repoUsuario.findByUsername(registroDTO.getUsername()));
        if (usuarioExistente.isPresent()) {
            throw new NombreUsuarioYaExisteException("¡Este nombre de usuario ya está registrado!");
        }

        Usuario usuario = mapRegistrationDtoToUser(registroDTO);

        if (TipoRol.CLIENTE.equals(registroDTO.getTipoRol())) {
            Cliente cliente = Cliente.builder().usuario(usuario).build();
            repoCliente.save(cliente);
        } else if (TipoRol.HOTEL_MANAGER.equals(registroDTO.getTipoRol())) {
            HotelManager hotelManager = HotelManager.builder().usuario(usuario).build();
            repoHotelManager.save(hotelManager);
        }

        Usuario usuarioGuardado = repoUsuario.save(usuario);
        log.info("Se ha guardado correctamente el nuevo usuario: {}", registroDTO.getUsername());
        return usuarioGuardado;
    }

    @Override
    public Usuario buscarUsuarioByUsername(String username) {
        return repoUsuario.findByUsername(username);
    }

    @Override
    public UsuarioDTO buscarUsuarioDTOByUsername(String username) {
        Optional<Usuario> usuarioOpcional = Optional.ofNullable(repoUsuario.findByUsername(username));
        Usuario usuario = usuarioOpcional.orElseThrow(() -> new UsernameNotFoundException("Nombre de usuario no encontrado"));

        return mapUserToUserDto(usuario);
    }

    @Override
    public UsuarioDTO buscarUsuarioById(Long id) {
        Usuario usuario = repoUsuario.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        return mapUserToUserDto(usuario);
    }

    @Override
    public List<UsuarioDTO> buscarAllUsuarios() {
        List<Usuario> usuarioList = repoUsuario.findAll();

        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();
        for (Usuario usuario : usuarioList) {
            UsuarioDTO usuarioDTO = mapUserToUserDto(usuario);
            usuarioDTOList.add(usuarioDTO);
        }
        return usuarioDTOList;
    }

    @Override
    @Transactional
    public void actualizarUsuario(UsuarioDTO usuarioDTO) {
        log.info("Intentando actualizar el usuario con ID: {}", usuarioDTO.getId());

        Usuario usuario = repoUsuario.findById(usuarioDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (usernameExistsAndNotSameUser(usuarioDTO.getUsername(), usuario.getId())) {
            throw new NombreUsuarioYaExisteException("Intentando actualizar el usuario con ID: {}");
        }

        setFormattedDataToUsuario(usuario, usuarioDTO);
        repoUsuario.save(usuario);
        log.info("Se ha actualizado correctamente el usuario existente con ID: {}", usuarioDTO.getId());
    }

    @Override
    @Transactional
    public void actualizarUsuarioLoggeado(UsuarioDTO usuarioDTO) {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario loggedInUsuario = repoUsuario.findByUsername(loggedInUsername);
        log.info("Intentando actualizar el usuario conectado con ID: {}", loggedInUsuario.getId());

        if (usernameExistsAndNotSameUser(usuarioDTO.getUsername(), loggedInUsuario.getId())) {
            throw new NombreUsuarioYaExisteException("Este nombre de usuario ya esta registrado!");
        }

        setFormattedDataToUsuario(loggedInUsuario, usuarioDTO);
        repoUsuario.save(loggedInUsuario);
        log.info("Se ha actualizado correctamente el usuario conectado con ID: {}", loggedInUsuario.getId());

        // Create new authentication token
        updateAuthentication(usuarioDTO);
    }

    @Override
    public void borrarUsuarioById(Long id) {
        log.info("Intentando eliminar el usuario con ID: {}", id);
        repoUsuario.deleteById(id);
        log.info("Se ha eliminado correctamente el usuario con ID: {}", id);
    }

    // TODO: 23.07.2023
    @Override
    public Usuario resetPassword(ResetearPasswordDTO resetearPasswordDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = auth.getName();

        Usuario usuario = repoUsuario.findByUsername(loggedInUsername);
        if (usuario == null) {
            throw new UsernameNotFoundException("Useruario no encontrado");
        }
        if (!passwordEncoder.matches(resetearPasswordDTO.getAntiguaPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("La antigua contrasenia es incorrecta");
        }
        usuario.setPassword(passwordEncoder.encode(resetearPasswordDTO.getNuevaPassword()));
        return repoUsuario.save(usuario);
    }

    private Usuario mapRegistrationDtoToUser(RegistroUsuarioDTO registroDTO) {
        Rol usuarioRol = repoRol.findByTipoRol(registroDTO.getTipoRol());
        return Usuario.builder()
                .username(registroDTO.getUsername().trim())
                .password(passwordEncoder.encode(registroDTO.getPassword()))
                .nombre(formatText(registroDTO.getNombre()))
                .apellido(formatText(registroDTO.getApellido()))
                .rol(usuarioRol)
                .build();
    }

    private UsuarioDTO mapUserToUserDto(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .rol(usuario.getRol())
                .build();
    }

    private boolean usernameExistsAndNotSameUser(String username, Long usuarioId) {
        Optional<Usuario> existingUserWithSameUsername = Optional.ofNullable(repoUsuario.findByUsername(username));
        return existingUserWithSameUsername.isPresent() && !existingUserWithSameUsername.get().getId().equals(usuarioId);
    }

    private String formatText(String text) {
        return StringUtils.capitalize(text.trim());
    }

    private void setFormattedDataToUsuario(Usuario usuario, UsuarioDTO usuarioDTO) {
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setNombre(formatText(usuarioDTO.getNombre()));
        usuario.setApellido(formatText(usuarioDTO.getApellido()));
    }

   
    private void updateAuthentication(UsuarioDTO usuarioDTO) {
        Usuario usuario = repoUsuario.findByUsername(usuarioDTO.getUsername());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROL_" + usuario.getRol().getTipoRol().name()));

        UserDetails newUserDetails = new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                authorities
        );

        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                newUserDetails,
                null,
                newUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }

}
