package deposit.dto.account;

import lombok.Getter;

@Getter
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
}
