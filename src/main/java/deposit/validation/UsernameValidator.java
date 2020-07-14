package deposit.validation;

import deposit.validation.annotation.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Валидация логина через аннотацию @Username.
 *
 * Требует цифры, буквы, знаки подчеркивания, точки и тире в количестве от 3 до 30.
 */

public class UsernameValidator implements ConstraintValidator<Username, String> {

    @Override
    public void initialize(Username constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String regex = "^[\\w-.]{3,30}$";
        return Pattern.compile(regex).matcher(s).matches();
    }
}