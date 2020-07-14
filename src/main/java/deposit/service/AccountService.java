package deposit.service;

import deposit.dto.account.ChangePasswordDto;
import deposit.dto.account.RegisterDto;
import deposit.entity.Account;
import deposit.exception.*;
import deposit.repository.AccountRepository;
import deposit.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Сервисный класс для работы с аккаунтами.
 *
 * Также реализует интерфейс UserDetailsService для поддержки Security.
 * Позволяет получить пользователя по Username, зарегистрировать нового и сменить пароль.
 */

@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return JwtUser.create(accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Аккаунт с логином=" + username + " не найден")));
    }

    public void registerNewUser(RegisterDto registerDto) {
        String username = registerDto.getUsername();
        String email = registerDto.getEmail();
        boolean usernameExists = accountRepository.existsByUsername(username);
        boolean emailExists = accountRepository.existsByEmail(email);
        if (usernameExists && emailExists)
            throw new UsernameAndEmailAlreadyExistException();
        else if (usernameExists)
            throw new UsernameAlreadyExistsException();
        else if (emailExists)
            throw new EmailAlreadyExistsException();
        Account account = new Account();
        account.setUsername(registerDto.getUsername());
        account.setEmail(registerDto.getEmail());
        account.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        accountRepository.save(account);
    }

    public void changePassword(ChangePasswordDto changePasswordDto) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentPassword = jwtUser.getPassword();
        if (changePasswordDto.getOldPassword().equals(changePasswordDto.getNewPassword()))
            throw new OldAndNewPasswordEqualException();
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), currentPassword))
            throw new InvalidPasswordException();
        accountRepository.updatePassword(jwtUser.getId(), passwordEncoder.encode(changePasswordDto.getNewPassword()));
    }
}










