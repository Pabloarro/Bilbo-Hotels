package es.deusto.bilboHotels.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unidirectional relationship due to business logic and performance considerations
    // Logic: Querying Availability based on specific dates, rather than getting all Availability entities for a Hotel
    @ManyToOne
    @JoinColumn(nullable = false)
    private Hotel hotel;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Habitacion habitacion;

    @Column(nullable = false)
    private int habitacionesDisponibles;

    @Override
    public String toString() {
        return "Disponibilidad{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", fecha=" + fecha +
                ", habitacion=" + habitacion +
                ", habitacionesDisponibles=" + habitacionesDisponibles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disponibilidad that = (Disponibilidad) o;
        return Objects.equals(id, that.id) && Objects.equals(hotel, that.hotel) && Objects.equals(habitacion, that.habitacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotel, habitacion);
    }
}
