package es.deusto.bilboHotels.service.impl;

import es.deusto.bilboHotels.model.Direccion;
import es.deusto.bilboHotels.model.dto.DireccionDTO;
import es.deusto.bilboHotels.repository.RepoDireccion;
import es.deusto.bilboHotels.service.ServicioDireccion;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioDireccionImpl implements ServicioDireccion {

    private final RepoDireccion repoDireccion;

    @Override
    public Direccion guardarDireccion(DireccionDTO direccionDTO) {
        log.info("Intentando guardar una nueva dirección: {}", direccionDTO.toString());
        Direccion direccion = mapDireccionDtoToDireccion(direccionDTO);

        Direccion direccionGuardada = repoDireccion.save(direccion);
        log.info("Se ha guardado correctamente la nueva dirección con el ID: {}", direccion.getId());
        return direccionGuardada;
    }

    @Override
    public DireccionDTO buscarDireccionById(Long id) {
        Direccion direccion = repoDireccion.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Direccion no encontrada"));

        return mapDireccionToDireccionDto(direccion);
    }

    @Override
    public Direccion actualizarDireccion(DireccionDTO direccionDTO) {
        log.info("Intentando actualizar la dirección con ID: {}", direccionDTO.getId());
        Direccion direccionExistente = repoDireccion.findById(direccionDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Direccion no encontrada"));

        setFormattedDataToAddress(direccionExistente, direccionDTO);

        Direccion direccionActualizada = repoDireccion.save(direccionExistente);
        log.info("Se ha actualizado correctamente la dirección con ID: {}", direccionExistente.getId());
        return direccionActualizada;
    }

    @Override
    public void borrarDireccion(Long id) {
        log.info("Intentando eliminar la dirección con ID: {}", id);
        if (!repoDireccion.existsById(id)) {
            log.error("Error al eliminar la dirección. La dirección con ID: {} no se encontró.", id);
            throw new EntityNotFoundException("Direccion no encontrada");
        }
        repoDireccion.deleteById(id);
        log.info("Se ha eliminado correctamente la dirección con ID: {}", id);
    }

    @Override
    public Direccion mapDireccionDtoToDireccion(DireccionDTO dto) {
        return Direccion.builder()
                .lineaDireccion(formatText(dto.getLineaDireccion()))
                .ciudad(formatText(dto.getCiudad()))
                .pais(formatText(dto.getPais()))
                .build();
    }

    @Override
    public DireccionDTO mapDireccionToDireccionDto(Direccion address) {
        return DireccionDTO.builder()
                .id(address.getId())
                .lineaDireccion(address.getLineaDireccion())
                .ciudad(address.getCiudad())
                .pais(address.getPais())
                .build();
    }

    private String formatText(String text) {
        return StringUtils.capitalize(text.trim());
    }

    private void setFormattedDataToAddress(Direccion address, DireccionDTO direccionDTO) {
        address.setLineaDireccion(formatText(direccionDTO.getLineaDireccion()));
        address.setCiudad(formatText(direccionDTO.getCiudad()));
        address.setPais(formatText(direccionDTO.getPais()));
    }
}
