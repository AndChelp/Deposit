package deposit.validation;

import deposit.validation.annotation.Bik;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Валидация БИК через аннотацию @Bik.
 *
 * Требует строго 9 цифр.
 */

public class BikValidator implements ConstraintValidator<Bik, String> {

    @Override
    public void initialize(Bik constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String regex = "^[\\d]{9}$";
        return Pattern.compile(regex).matcher(s).matches();
    }
}
