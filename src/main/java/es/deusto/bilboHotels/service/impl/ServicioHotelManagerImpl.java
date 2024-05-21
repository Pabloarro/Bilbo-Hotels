package es.deusto.bilboHotels.service.impl;

import es.deusto.bilboHotels.model.HotelManager;
import es.deusto.bilboHotels.model.Usuario;
import es.deusto.bilboHotels.repository.RepoHotelManager;
import es.deusto.bilboHotels.service.ServicioHotelManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioHotelManagerImpl implements ServicioHotelManager {

    private final RepoHotelManager hotelManagerRepository;

    @Override
    public HotelManager findByUsuario(Usuario usuario) {
        log.info("Intentando encontrar el manager de hotel para el ID de usuario: {}", usuario.getId());
        return hotelManagerRepository.findByUsuario(usuario)
                .orElseThrow(() -> new EntityNotFoundException("HotelManager no encontrado para usuario " + usuario.getUsername()));
    }
}
