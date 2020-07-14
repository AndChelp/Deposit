package deposit.service;

import deposit.dto.bank.BankCreationDto;
import deposit.dto.bank.BankDto;
import deposit.entity.Bank;
import deposit.repository.BankRepository;
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
class BankServiceTest {
    @InjectMocks
    private BankService bankService;
    @Mock
    private BankRepository bankRepository;
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
    void addBank() {
        bankService.addBank(new BankCreationDto("test", "test"));
        Mockito.verify(bankRepository, Mockito.times(1))
                .save(Mockito.any());
    }

    @Test
    void updateBank() {
        Bank bank = new Bank();
        bank.setAccountId(jwtUser.getId());
        BankDto bankDto = new BankDto();
        Mockito.when(bankRepository.findById(bankDto.getId())).thenReturn(Optional.of(bank));
        bankService.updateBank(bankDto);
        Mockito.verify(bankRepository, Mockito.times(1))
                .findById(bankDto.getId());
        Mockito.verify(bankRepository, Mockito.times(1))
                .save(bank);

    }

    @Test
    void deleteBank() {
        int bankId = 1;
        Mockito.when(bankRepository.getAccountIdByBankId(bankId)).thenReturn(Optional.of(1));
        bankService.deleteBank(bankId);
        Mockito.verify(bankRepository, Mockito.times(1))
                .disableBankById(bankId);
    }
}




















