package deposit.dto.account;

import deposit.validation.annotation.Password;
import deposit.validation.annotation.Username;
import lombok.Getter;

@Getter
public class LoginDto {
    @Username
    private String username;
    @Password
    private String password;
}
