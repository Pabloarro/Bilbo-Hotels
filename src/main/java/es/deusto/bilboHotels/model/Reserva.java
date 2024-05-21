package es.deusto.bilboHotels.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroConfirmacion;

    @CreationTimestamp
    private LocalDateTime fechaReserva;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Hotel hotel;

    @Column(nullable = false)
    private LocalDate fechaCheckIn;

    @Column(nullable = false)
    private LocalDate fechaCheckOut;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HabitacionReservada> habitacionReservadas = new ArrayList<>();

    @OneToOne(mappedBy = "reserva")
    private Pago pago;

    @PrePersist
    protected void onCreate() {
        this.numeroConfirmacion = UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", numeroConfirmacion='" + numeroConfirmacion + '\'' +
                ", fechaReserva=" + fechaReserva +
                ", cliente=" + cliente +
                ", hotel=" + hotel +
                ", fechaCheckIn=" + fechaCheckIn +
                ", fechaCheckOut=" + fechaCheckOut +
                ", habitacionReservadas=" + habitacionReservadas +
                ", pago=" + pago +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva booking = (Reserva) o;
        return Objects.equals(id, booking.id) && Objects.equals(numeroConfirmacion, booking.numeroConfirmacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroConfirmacion);
    }
}
