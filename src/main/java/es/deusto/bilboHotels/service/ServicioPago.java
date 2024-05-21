package es.deusto.bilboHotels.service;

import es.deusto.bilboHotels.model.Reserva;
import es.deusto.bilboHotels.model.Pago;
import es.deusto.bilboHotels.model.dto.InicioReservaDTO;

public interface ServicioPago {

    Pago savePayment(InicioReservaDTO inicioReservaDTO, Reserva reserva);
}
