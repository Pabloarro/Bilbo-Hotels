package es.deusto.bilboHotels.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {

    private Long id;

    @NotBlank(message = "La línea de dirección no puede estar vacía.")
    @Pattern(regexp = "^[A-Za-z0-9 .,:-]*$", message = "La línea de dirección solo puede contener letras, números y algunos caracteres especiales (., :, -).")
    private String lineaDireccion;

    @NotBlank(message = "La ciudad no puede estar vacia")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z ]+$", message = "La ciudad solo puede contener letras")
    private String ciudad;

    @NotBlank(message = "El país no puede estar vacío")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z ]+$", message = "El país solo puede contener letras")
    private String pais;

    @Override
public String toString() {
    return "DireccionDTO{" +
            "lineaDireccion='" + lineaDireccion + '\'' +
            ", ciudad='" + ciudad + '\'' +
            ", pais='" + pais + '\'' +
            '}';
}
}
