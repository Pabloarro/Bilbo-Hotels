package es.deusto.bilboHotels.service;

import es.deusto.bilboHotels.model.Reserva;
import es.deusto.bilboHotels.model.dto.ReservaDTO;
import es.deusto.bilboHotels.model.dto.InicioReservaDTO;

import java.util.List;

public interface ServicioReserva {

    Reserva saveBooking(InicioReservaDTO inicioReservaDTO, Long clienteId);

    ReservaDTO confirmarReserva(InicioReservaDTO inicioReservaDTO, Long clienteId);

    List<ReservaDTO> buscarAllReservas();

    ReservaDTO buscarReservaById(Long reservaId);

    List<ReservaDTO> buscarReservasByClienteId(Long clienteId);

    ReservaDTO BuscarReservasByIdAndClienteId(Long reservaId, Long clienteId);

    List<ReservaDTO> buscarReservasByManagerId(Long managerId);

    ReservaDTO buscarReservaByIdAndManagerId(Long reservaId, Long managerId);

    ReservaDTO mapReservaModelToReservaDto(Reserva reserva);

}
