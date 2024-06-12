package es.deusto.bilboHotels.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionBuilder {

    private String calle;
    private String numero;
    private String ciudad;
    private String codigoPostal;

    public DireccionBuilder setCalle(String calle) {
        this.calle = calle;
        return this;
    }

    public DireccionBuilder setNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public DireccionBuilder setCiudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public DireccionBuilder setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public Direccion build() {
        return new Direccion(calle + " " + numero, ciudad, codigoPostal);
    }
}

