package es.deusto.bilboHotels.repository;

import es.deusto.bilboHotels.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoReserva extends JpaRepository<Reserva, Long> {

    List<Reserva> findBookingsByClienteId(Long clienteId);

    Optional<Reserva> findBookingByIdAndClienteId(Long reservaId, Long clienteId);

    List<Reserva> findBookingsByHotelId(Long hotelId);

    Optional<Reserva> findBookingByIdAndHotel_HotelManagerId(Long reservaId, Long hotelManagerId);

}
