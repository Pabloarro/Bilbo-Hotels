package es.deusto.bilboHotels.model;

import es.deusto.bilboHotels.model.enums.Divisa;
import es.deusto.bilboHotels.model.enums.MetodoPago;
import es.deusto.bilboHotels.model.enums.EstadoPago;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transaccionId;

    @CreationTimestamp
    private LocalDateTime fechaPago;

    @OneToOne
    @JoinColumn(nullable = false)
    private Reserva reserva;

    @Column(nullable = false)
    private BigDecimal precioTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estadoPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Divisa divisa;

    @PrePersist
    protected void onCreate() {
        this.transaccionId = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", transaccionId=" + transaccionId +
                ", fechaPago=" + fechaPago +
                ", reserva=" + reserva +
                ", precioTotal=" + precioTotal +
                ", estadoPago=" + estadoPago +
                ", metodoPago=" + metodoPago +
                ", divisa=" + divisa +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pago payment = (Pago) o;
        return Objects.equals(id, payment.id) && Objects.equals(transaccionId, payment.transaccionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transaccionId);
    }

}
