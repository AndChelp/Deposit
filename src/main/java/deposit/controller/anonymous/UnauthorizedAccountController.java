package deposit.controller.anonymous;

import deposit.dto.account.LoginDto;
import deposit.dto.account.RegisterDto;
import deposit.dto.utils.JwtDto;
import deposit.security.jwt.JwtUtils;
import deposit.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Публичный контроллер для авторизации и регистрации.
 *
 * Доступные запросы:
 * [POST]   /api/public/account/login (body){LoginDto}
 * [POST]   /api/public/account/register (body){RegisterDto}
 */

@RestController
@RequestMapping("/api/public/account")
public class UnauthorizedAccountController {
    private final AccountService accountService;
    private final JwtUtils jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UnauthorizedAccountController(AccountService accountService, JwtUtils jwtProvider, AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(
                //При успешной авторизации возвращает токен, иначе ошибка авторизации
                new JwtDto(jwtAuthentication(loginDto.getUsername(), loginDto.getPassword())),
                HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtDto> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        accountService.registerNewUser(registerDto);
        return new ResponseEntity<>(
                //При успешной авторизации возвращает токен, иначе ошибка регистрации
                new JwtDto(jwtAuthentication(registerDto.getUsername(), registerDto.getPassword())),
                HttpStatus.CREATED);
    }

    private String jwtAuthentication(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateJwtToken(authentication);
    }
}
