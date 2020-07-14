package deposit.validation;

import deposit.validation.annotation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Валидация пароля через аннотацию @Password.
 *
 * Требует цифры, буквы, знаки подчеркивания, точки и тире в количестве от 6 до 60.
 */

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String regex = "^[\\w-.]{6,60}$";
        return Pattern.compile(regex).matcher(s).matches();
    }
}
