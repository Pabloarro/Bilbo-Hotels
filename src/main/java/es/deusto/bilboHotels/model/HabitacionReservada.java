package es.deusto.bilboHotels.model;

import es.deusto.bilboHotels.model.enums.TipoHabitacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitacionReservada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Reserva reserva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoHabitacion tipoHabitacion;

    @Column(nullable = false)
    private int contar;

    @Override
    public String toString() {
        return "HabiracionReservada{" +
                "id=" + id +
                ", reserva=" + reserva +
                ", tipoHabitacion=" + tipoHabitacion +
                ", contar=" + contar +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HabitacionReservada that = (HabitacionReservada) o;
        return Objects.equals(id, that.id) && Objects.equals(reserva, that.reserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reserva);
    }
}
