package es.deusto.bilboHotels.validation.annotation;

import es.deusto.bilboHotels.validation.validator.CaducidadTarjetaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CaducidadTarjetaValidator.class)
public @interface CaducidadTarjeta {

    String message() default "Fecha de expiracion invalida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
