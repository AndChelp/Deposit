package deposit.service;

import deposit.dto.account.ChangePasswordDto;
import deposit.dto.account.RegisterDto;
import deposit.exception.InvalidPasswordException;
import deposit.repository.AccountRepository;
import deposit.security.jwt.JwtUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private JwtUser jwtUser;

    @BeforeEach
    void setup() {
        jwtUser = new JwtUser(
                1,
                "testUser",
                "$2y$10$nijNAFJO1rMd51boSxJcrOUHMlZhxoFGdiKoIhjAvU0slDCi1yZW.",
                true);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(jwtUser);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void registerNewUser() {
        RegisterDto registerDto = new RegisterDto("username", "email", "password");
        accountService.registerNewUser(registerDto);
        Mockito.verify(accountRepository, Mockito.times(1))
                .existsByEmail(registerDto.getEmail());
        Mockito.verify(accountRepository, Mockito.times(1))
                .existsByUsername(registerDto.getUsername());
        Mockito.verify(passwordEncoder, Mockito.times(1))
                .encode(registerDto.getPassword());
        Mockito.verify(accountRepository, Mockito.times(1))
                .save(Mockito.any());
    }

    @Test
    void changePassword() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("testPass", "newPass");
        Assertions.assertThrows(InvalidPasswordException.class, () ->
                accountService.changePassword(changePasswordDto));
        Mockito.verify(passwordEncoder, Mockito.times(1))
                .matches(changePasswordDto.getOldPassword(), jwtUser.getPassword());
    }
}
























