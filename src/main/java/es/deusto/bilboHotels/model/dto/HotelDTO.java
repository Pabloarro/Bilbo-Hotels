package es.deusto.bilboHotels.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class HotelDTO {

    private Long id;

    @NotBlank(message = "El nombre del hotel no puede estar vacio")
    @Pattern(regexp = "^(?!\\s*$)[A-Za-z0-9 ]+$", message = "El nombre del hotel solo puede contener letras y numeros")
    private String nombre;

    @Valid
    private DireccionDTO direccionDTO;

    @Valid
    private List<HabitacionDTO> habitacionDTOs = new ArrayList<>();

    private String managerUsername;

}
