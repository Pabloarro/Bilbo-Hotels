package es.deusto.bilboHotels.service.impl;

import es.deusto.bilboHotels.model.Reserva;
import es.deusto.bilboHotels.model.Pago;
import es.deusto.bilboHotels.model.dto.InicioReservaDTO;
import es.deusto.bilboHotels.model.enums.Divisa;
import es.deusto.bilboHotels.model.enums.MetodoPago;
import es.deusto.bilboHotels.model.enums.EstadoPago;
import es.deusto.bilboHotels.repository.RepoPago;
import es.deusto.bilboHotels.service.ServicioPago;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioPagoImpl implements ServicioPago {

    private final RepoPago repoPago;

    @Override
    public Pago savePayment(InicioReservaDTO inicioReservaDTO, Reserva reserva) {
        Pago payment = Pago.builder()
                .reserva(reserva)
                .precioTotal(inicioReservaDTO.getPrecioTotal())
                .estadoPago(EstadoPago.COMPLETADO) // Assuming the payment is completed
                .metodoPago(MetodoPago.TARJETA_CREDITO) // Default to CREDIT_CARD
                .divisa(Divisa.USD) // Default to USD
                .build();

        Pago pagoGuardado = repoPago.save(payment);
        log.info("Pago guardado con ID transaccion : {}", pagoGuardado.getTransaccionId());

        return pagoGuardado;
    }
}
