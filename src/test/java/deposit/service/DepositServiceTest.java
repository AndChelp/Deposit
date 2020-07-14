package deposit.service;

import deposit.dto.deposit.DepositCreationDto;
import deposit.dto.deposit.DepositDto;
import deposit.entity.Deposit;
import deposit.repository.DepositRepository;
import deposit.security.jwt.JwtUser;
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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {
    @InjectMocks
    private DepositService depositService;
    @Mock
    private DepositRepository depositRepository;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    private JwtUser jwtUser;

    @BeforeEach
    void setup() {
        jwtUser = new JwtUser(
                1,
                "testUser",
                "$2y$10$nijNAFJO1rMd51boSxJcrOUHMlZhxoFGdiKoIhjAvU0slDCi1yZW.",
                true);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(jwtUser);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void addDeposit() {
        DepositCreationDto depositCreationDto = new DepositCreationDto();
        depositService.addDeposit(depositCreationDto);
        Mockito.verify(depositRepository, Mockito.times(1))
                .save(Mockito.any());

    }

    @Test
    void updateDeposit() {
        Deposit deposit = new Deposit();
        deposit.setAccountId(jwtUser.getId());
        DepositDto depositDto = new DepositDto();
        Mockito.when(depositRepository.findById(depositDto.getId())).thenReturn(Optional.of(deposit));
        depositService.updateDeposit(depositDto);
        Mockito.verify(depositRepository, Mockito.times(1))
                .findById(depositDto.getId());
        Mockito.verify(depositRepository, Mockito.times(1))
                .save(deposit);
    }

    @Test
    void deleteDeposit() {
        int depositId = 1;
        Mockito.when(depositRepository.getAccountByDepositId(depositId)).thenReturn(Optional.of(1));
        depositService.deleteDeposit(depositId);
        Mockito.verify(depositRepository, Mockito.times(1))
                .disableDepositById(depositId);
        Mockito.verify(depositRepository, Mockito.times(1))
                .disableAllDepositsByClient(depositId);
    }
}





