package es.deusto.bilboHotels.model.dto;

import es.deusto.bilboHotels.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "La dirección de correo electrónico no puede estar vacía.")
    @Email(message = "Email invalido")
    private String username;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z ]+$", message = "El nombre solo puede contener letras.")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío.")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z ]+$", message = "El apellido solo puede contener letras")
    private String apellido;

    private Rol rol;

}
