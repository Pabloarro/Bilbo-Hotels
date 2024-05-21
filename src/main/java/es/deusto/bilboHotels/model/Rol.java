package es.deusto.bilboHotels.model;

import es.deusto.bilboHotels.model.enums.TipoRol;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private TipoRol tipoRol;

    public Rol(TipoRol tipoRol) {
        this.tipoRol = tipoRol;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", nombre=" + tipoRol +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rol rol = (Rol) o;
        return Objects.equals(id, rol.id) && tipoRol == rol.tipoRol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoRol);
    }
}

