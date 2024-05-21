package es.deusto.bilboHotels.model.dto;

import es.deusto.bilboHotels.model.enums.TipoRol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistroUsuarioDTO {

    @NotBlank(message = "La dirección de correo electrónico no puede estar vacía.")
    @Email(message = "Email invalido")
    private String username;

    @NotBlank(message = "La contrasenia no puede estar vacía.")
    @Size(min = 6, max = 20, message = "La contrasenia debe tener entre 6 y 20 caracteres.")
    private String password;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z ]+$", message = "El nombre solo puede contener letras")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z ]+$", message = "El apellido solo puede contener letras.")
    private String apellido;

    private TipoRol tipoRol;

}
