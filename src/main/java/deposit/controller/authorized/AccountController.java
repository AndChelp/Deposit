package deposit.controller.authorized;

import deposit.dto.account.ChangePasswordDto;
import deposit.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/password/change")
    public void changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        accountService.changePassword(changePasswordDto);
    }
}
