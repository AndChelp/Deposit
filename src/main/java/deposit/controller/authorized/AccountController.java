package deposit.controller.authorized;

import deposit.dto.account.ChangePasswordDto;
import deposit.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Контроллер для управления аккаунта.
 *
 * Доступные запросы:
 * [POST]   /api/account/password/change (body){ChangePasswordDto}
 */

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/password/change")
    public void changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        //Меняет пароль, либо выкидывает ошибку
        accountService.changePassword(changePasswordDto);
    }
}
