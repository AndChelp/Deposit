package deposit.dto.account;

import deposit.validation.annotation.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    @Password
    private String oldPassword;
    @Password
    private String newPassword;
}
