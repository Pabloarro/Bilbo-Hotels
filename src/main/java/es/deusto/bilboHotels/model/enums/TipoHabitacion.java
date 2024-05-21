package es.deusto.bilboHotels.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoHabitacion {

    INDIVIDUAL(1),
    DOBLE(2);

    private final int capacity;

}
