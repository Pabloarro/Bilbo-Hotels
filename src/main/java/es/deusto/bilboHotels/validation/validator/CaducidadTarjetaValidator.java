package es.deusto.bilboHotels.validation.validator;

import es.deusto.bilboHotels.validation.annotation.CaducidadTarjeta;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.YearMonth;

public class CaducidadTarjetaValidator implements ConstraintValidator<CaducidadTarjeta, String> {

    @Override
    public void initialize(CaducidadTarjeta constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || !value.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            return false;
        }

        try {
            String[] parts = value.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = 2000 + Integer.parseInt(parts[1]); // Assuming 21st century

            YearMonth cardExpiry = YearMonth.of(year, month);
            YearMonth currentMonth = YearMonth.now();

            return cardExpiry.isAfter(currentMonth) || cardExpiry.equals(currentMonth);
        } catch (NumberFormatException | DateTimeException e) {
            return false;
        }
    }
}