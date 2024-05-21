package es.deusto.bilboHotels.service;

import es.deusto.bilboHotels.model.Direccion;
import es.deusto.bilboHotels.model.dto.DireccionDTO;

public interface ServicioDireccion {

    Direccion guardarDireccion(DireccionDTO addressDTO);

    DireccionDTO buscarDireccionById(Long id);

    Direccion actualizarDireccion(DireccionDTO direccionDTO);

    void borrarDireccion(Long id);

    Direccion mapDireccionDtoToDireccion(DireccionDTO dto);

    DireccionDTO mapDireccionToDireccionDto(Direccion direccion);


}
