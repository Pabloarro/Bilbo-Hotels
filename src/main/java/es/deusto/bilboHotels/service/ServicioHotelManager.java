package es.deusto.bilboHotels.service;

import es.deusto.bilboHotels.model.HotelManager;
import es.deusto.bilboHotels.model.Usuario;

public interface ServicioHotelManager {

    HotelManager findByUsuario(Usuario usuario);

}
