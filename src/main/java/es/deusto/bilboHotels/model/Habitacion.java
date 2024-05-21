package es.deusto.bilboHotels.model;

import es.deusto.bilboHotels.model.enums.TipoHabitacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Hotel hotel;

    @Enumerated(EnumType.STRING)
    private TipoHabitacion tipoHabitacion;

    private int contadorHabitacion;

    private double precioPorNoche;

    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Disponibilidad> availabilities = new ArrayList<>();

    @Override
    public String toString() {
        return "Habitacion{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", tipoHabitacion=" + tipoHabitacion +
                ", contadorHabitacion=" + contadorHabitacion +
                ", precioPorNoche=" + precioPorNoche +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion room = (Habitacion) o;
        return Objects.equals(id, room.id) && Objects.equals(hotel, room.hotel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotel);
    }
}
