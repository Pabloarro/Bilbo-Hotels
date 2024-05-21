package es.deusto.bilboHotels.model.dto;

import es.deusto.bilboHotels.validation.annotation.CaducidadTarjeta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarjetaPagoDTO {

    @NotBlank(message = "El nombre del titular no puede estar vacio")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "El nombre del tutular solo puede contener letras y espacios")
    @Size(min = 3, max = 50, message = "El nombre del titular tiene qye tener entre 3 and 50 characters")
    private String nombreTitular;

    // 16 digits + Luhn check
    @CreditCardNumber(message = "Numero de tarjeta invalido")
    private String numeroTarjeta;

    @CaducidadTarjeta
    private String caducidadTarjeta;

    @Pattern(regexp = "^\\d{3}$", message = "CVC tiene que ser de 3 digitos")
    private String cvc;
}
