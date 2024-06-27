package es.deusto.bilboHotels.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.deusto.bilboHotels.model.Pago;
import es.deusto.bilboHotels.model.Reserva;
import es.deusto.bilboHotels.model.enums.Divisa;
import es.deusto.bilboHotels.model.enums.EstadoPago;
import es.deusto.bilboHotels.model.enums.MetodoPago;

public class PagoTest {
    private Pago pago1;
    private Pago pago2;
    private Reserva reserva;

    @Before
    public void setUp() {
        reserva = new Reserva();
        pago1 = new Pago();
        pago1.setId(1L);
        pago1.setReserva(reserva);
        pago1.setPrecioTotal(new BigDecimal("100.00"));
        pago1.setEstadoPago(EstadoPago.PENDIENTE);
        pago1.setMetodoPago(MetodoPago.TARJETA_CREDITO);
        pago1.setDivisa(Divisa.EUR);
        //pago1.onCreate(); MÉTODO ONCREATE EN CLASE PAGO NO VISIBLE

        pago2 = new Pago();
        pago2.setId(1L);
        pago2.setReserva(reserva);
        pago2.setPrecioTotal(new BigDecimal("100.00"));
        pago2.setEstadoPago(EstadoPago.PENDIENTE);
        pago2.setMetodoPago(MetodoPago.TARJETA_CREDITO);
        pago2.setDivisa(Divisa.EUR);
        //pago2.onCreate(); MÉTODO ONCREATE EN CLASE PAGO NO VISIBLE
    }

    /*@Test
    public void testOnCreate() {
        Pago pago = new Pago();
        //pago.onCreate(); MÉTODO ONCREATE EN CLASE PAGO NO VISIBLE
        assertNotNull(pago.getTransaccionId());
        assertTrue(pago.getTransaccionId().length() > 0);
    }*/

    /*@Test
    public void testToString() {
        String expected = "Pago{" +
                "id=" + pago1.getId() +
                ", transaccionId=" + pago1.getTransaccionId() +
                ", fechaPago=" + pago1.getFechaPago() +
                ", reserva=" + pago1.getReserva() +
                ", precioTotal=" + pago1.getPrecioTotal() +
                ", estadoPago=" + pago1.getEstadoPago() +
                ", metodoPago=" + pago1.getMetodoPago() +
                ", divisa=" + pago1.getDivisa() +
                '}';
        assertEquals(expected, pago1.toString());
    }*/

    @Test
    public void testEquals() {
        assertTrue(pago1.equals(pago2));
        pago2.setId(2L);
        assertFalse(pago1.equals(pago2));
    }

    @Test
    public void testHashCode() {
        assertEquals(pago1.hashCode(), pago2.hashCode());
        pago2.setId(2L);
        assertNotEquals(pago1.hashCode(), pago2.hashCode());
    }
}
