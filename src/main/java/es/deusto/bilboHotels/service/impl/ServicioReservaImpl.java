package es.deusto.bilboHotels.service.impl;

import es.deusto.bilboHotels.model.*;
import es.deusto.bilboHotels.model.dto.DireccionDTO;
import es.deusto.bilboHotels.model.dto.ReservaDTO;
import es.deusto.bilboHotels.model.dto.InicioReservaDTO;
import es.deusto.bilboHotels.model.dto.HabitacionSeleccionDTO;
import es.deusto.bilboHotels.repository.RepoReserva;
import es.deusto.bilboHotels.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioReservaImpl implements ServicioReserva {

    private final RepoReserva repoReserva;
    private final ServicioDisponibilidad servicioDisponibilidad;
    private final ServicioPago servicioPago;
    private final ServicioCliente servicioCliente;
    private final ServicioHotel servicioHotel;


    @Override
    @Transactional
    public Reserva saveBooking(InicioReservaDTO bookingInitiationDTO, Long usuarioId) {
        validateBookingDates(bookingInitiationDTO.getFechaCheckIn(), bookingInitiationDTO.getFechaCheckOut());

        Cliente customer = servicioCliente.buscarByUsuarioId(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + usuarioId));

        Hotel hotel = servicioHotel.buscarHotelById(bookingInitiationDTO.getHotelId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado con ID: " + bookingInitiationDTO.getHotelId()));

        Reserva booking = mapBookingInitDtoToBookingModel(bookingInitiationDTO, customer, hotel);

        return repoReserva.save(booking);
    }

    @Override
    @Transactional
    public ReservaDTO confirmarReserva(InicioReservaDTO inicioReservaDTO, Long clienteId) {
        Reserva savedBooking = this.saveBooking(inicioReservaDTO, clienteId);
        Pago savedPayment = servicioPago.savePayment(inicioReservaDTO, savedBooking);
        savedBooking.setPago(savedPayment);
        repoReserva.save(savedBooking);
        servicioDisponibilidad.actualizarDisponibilidades(inicioReservaDTO.getHotelId(), inicioReservaDTO.getFechaCheckIn(),
        inicioReservaDTO.getFechaCheckOut(), inicioReservaDTO.getHabitacionSelecciones());
        return mapReservaModelToReservaDto(savedBooking);
    }

    @Override
    public List<ReservaDTO> buscarAllReservas() {
        List<Reserva> bookings = repoReserva.findAll();
        return bookings.stream()
                .map(this::mapReservaModelToReservaDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservaDTO buscarReservaById(Long reservaId) {
        Reserva reserva = repoReserva.findById(reservaId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + reservaId));
        return mapReservaModelToReservaDto(reserva);
    }

    @Override
    public List<ReservaDTO> buscarReservasByClienteId(Long clienteId) {
        List<Reserva> bookingDTOs = repoReserva.findBookingsByClienteId(clienteId);
        return bookingDTOs.stream()
                .map(this::mapReservaModelToReservaDto)
                .sorted(Comparator.comparing(ReservaDTO::getFechaCheckIn))
                .collect(Collectors.toList());
    }

    @Override
    public ReservaDTO BuscarReservasByIdAndClienteId(Long bookingId, Long clienteId) {
        Reserva booking = repoReserva.findBookingByIdAndClienteId(bookingId, clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID:" + bookingId));
        return mapReservaModelToReservaDto(booking);
    }

    @Override
    public List<ReservaDTO> buscarReservasByManagerId(Long managerId) {
        List<Hotel> hotels = servicioHotel.buscarTodosHotelesByManagerId(managerId);
        return hotels.stream()
                .flatMap(hotel -> repoReserva.findBookingsByHotelId(hotel.getId()).stream())
                .map(this::mapReservaModelToReservaDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservaDTO buscarReservaByIdAndManagerId(Long bookingId, Long managerId) {
        Reserva reserva = repoReserva.findBookingByIdAndHotel_HotelManagerId(bookingId, managerId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + bookingId + " Y manager ID: " + managerId));
        return mapReservaModelToReservaDto(reserva);
    }

    private void validateBookingDates(LocalDate checkinDate, LocalDate checkoutDate) {
        if (checkinDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de check in no puede estar en pasado");
        }
        if (checkoutDate.isBefore(checkinDate.plusDays(1))) {
            throw new IllegalArgumentException("La fecha de check out debe ser posterior a la de check in.");
        }
    }

    @Override
    public ReservaDTO mapReservaModelToReservaDto(Reserva reserva) {
        DireccionDTO addressDto = DireccionDTO.builder()
                .lineaDireccion(reserva.getHotel().getDireccion().getLineaDireccion())
                .ciudad(reserva.getHotel().getDireccion().getCiudad())
                .pais(reserva.getHotel().getDireccion().getPais())
                .build();

        List<HabitacionSeleccionDTO> roomSelections = reserva.getHabitacionReservadas().stream()
                .map(habitacion -> HabitacionSeleccionDTO.builder()
                        .tipoHabitacion(habitacion.getTipoHabitacion())
                        .contar(habitacion.getContar())
                        .build())
                .collect(Collectors.toList());

        Usuario customerUser = reserva.getCliente().getUsuario();

        return ReservaDTO.builder()
                .id(reserva.getId())
                .numeroConfirmacion(reserva.getNumeroConfirmacion())
                .fechaReserva(reserva.getFechaReserva())
                .clienteId(reserva.getCliente().getId())
                .hotelId(reserva.getHotel().getId())
                .fechaCheckIn(reserva.getFechaCheckIn())
                .fechaCheckOut(reserva.getFechaCheckOut())
                .seleccionesHabitacion(roomSelections)
                .precioTotal(reserva.getPago().getPrecioTotal())
                .nombreHotel(reserva.getHotel().getNombre())
                .direccionHotel(addressDto)
                .nombreCliente(customerUser.getNombre() + " " + customerUser.getApellido())
                .emailCliente(customerUser.getUsername())
                .estadoPago(reserva.getPago().getEstadoPago())
                .metodoPago(reserva.getPago().getMetodoPago())
                .build();
    }

    private Reserva mapBookingInitDtoToBookingModel(InicioReservaDTO inicioReservaDTO, Cliente cliente, Hotel hotel) {
        Reserva reserva = Reserva.builder()
                .cliente(cliente)
                .hotel(hotel)
                .fechaCheckIn(inicioReservaDTO.getFechaCheckIn())
                .fechaCheckOut(inicioReservaDTO.getFechaCheckOut())
                .build();

        for (HabitacionSeleccionDTO roomSelection : inicioReservaDTO.getHabitacionSelecciones()) {
            if (roomSelection.getContar() > 0) {
                HabitacionReservada habitacionReservada = HabitacionReservada.builder()
                        .reserva(reserva)
                        .tipoHabitacion(roomSelection.getTipoHabitacion())
                        .contar(roomSelection.getContar())
                        .build();
                reserva.getHabitacionReservadas().add(habitacionReservada);
            }
        }

        return reserva;
    }

}
