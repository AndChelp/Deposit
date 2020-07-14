package deposit.dto.account;

import deposit.validation.annotation.Password;
import deposit.validation.annotation.Username;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @Username
    private String username;
    @Email
    private String email;
    @Password
    private String password;
}
