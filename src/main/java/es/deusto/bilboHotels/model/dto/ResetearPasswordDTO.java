package es.deusto.bilboHotels.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetearPasswordDTO {

    @NotBlank(message = "La contrasenia no puede estar vacia")
    @Size(min = 6, max = 20, message = "La contrasenia debe tener entre 6 y 20 caracteres.")
    private String antiguaPassword;

    @NotBlank(message = "La contrasenia no puede estar vacía.")
    @Size(min = 6, max = 20, message = "La contrasenia debe tener entre 6 y 20 caracteres.")
    private String nuevaPassword;

    @NotBlank(message = "La contrasenia no puede estar vacia")
    @Size(min = 6, max = 20, message = "Password must be between 6 to 20 characters")
    private String confirmarNuevaPassword;

}
