package es.deusto.bilboHotels.security;

import es.deusto.bilboHotels.model.Usuario;
import es.deusto.bilboHotels.repository.RepoUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final RepoUsuario userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByUsername(username);

        if (usuario != null) {
            return new org.springframework.security.core.userdetails.User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    getAuthorities(usuario));
        } else {
            throw new UsernameNotFoundException("Nombre usuario o contraseña invalidos!");
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROL_" + usuario.getRol().getTipoRol().name()));
        return authorities;
    }

}
