package es.deusto.bilboHotels.model;

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
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @OneToOne(cascade = CascadeType.ALL)
    private Direccion direccion;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Habitacion> habitaciones = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private List<Reserva> reservas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private HotelManager hotelManager;

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", address=" + direccion +
                ", habitaciones=" + habitaciones +
                ", hotelManager=" + hotelManager +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(id, hotel.id) && Objects.equals(nombre, hotel.nombre) && Objects.equals(hotelManager, hotel.hotelManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, hotelManager);
    }
}
