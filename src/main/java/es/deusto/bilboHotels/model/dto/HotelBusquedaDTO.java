package es.deusto.bilboHotels.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HotelBusquedaDTO {

    @NotBlank(message = "La ciudad no puede estar vacia")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z '-]+$", message = "La ciudad solo debe contener letras, apóstrofes(') o guiones(-)")
    private String ciudad;

    @NotNull(message = "La fecha de Check-in no puede estar vacia")
    @FutureOrPresent(message = "La Fecha de Check-in no puede estar en pasado")
    private LocalDate fechaCheckIn;

    @NotNull(message = "La Fecha de Check-out no puede estar vacia")
    private LocalDate fechaCheckOut;
}
