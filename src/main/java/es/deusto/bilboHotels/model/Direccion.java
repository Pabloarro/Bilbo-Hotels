package es.deusto.bilboHotels.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lineaDireccion;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String pais;

    // Constructor que acepta los mismos parámetros que DireccionBuilder
    public Direccion(String lineaDireccion, String ciudad, String pais) {
        this.lineaDireccion = lineaDireccion;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", lineaDireccion='" + lineaDireccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion direccion = (Direccion) o;
        return Objects.equals(id, direccion.id) && Objects.equals(lineaDireccion, direccion.lineaDireccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lineaDireccion);
    }
}
