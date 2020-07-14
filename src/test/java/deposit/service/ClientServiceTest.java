package deposit.service;

import deposit.dto.client.AddressDto;
import deposit.dto.client.ClientCreationDto;
import deposit.dto.client.ClientDto;
import deposit.entity.Client;
import deposit.exception.ContentNotFoundException;
import deposit.repository.ClientRepository;
import deposit.repository.DepositRepository;
import deposit.security.jwt.JwtUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientRepository clientRepository;
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
    void addClient() {
        AddressDto addressDto = new AddressDto("123456", "tCity", "tStreet", "tHouse");
        clientService.addClient(new ClientCreationDto("tName", "tShortName", addressDto, 1));
        Mockito.verify(clientRepository, Mockito.times(1))
                .save(Mockito.any());
    }

    @Test
    void updateClient() {
        Client client = new Client();
        client.setAccountId(jwtUser.getId());
        AddressDto addressDto = new AddressDto("tIndex", "tCity", "tStreet", "tHouse");
        ClientDto clientDto = new ClientDto(1, "tName", "tShortName", addressDto, 0);
        Mockito.when(clientRepository.findById(clientDto.getId())).thenReturn(Optional.of(client));
        clientService.updateClient(clientDto);
        Mockito.verify(clientRepository, Mockito.times(1))
                .findById(clientDto.getId());
        Mockito.verify(clientRepository, Mockito.times(1))
                .save(client);
    }

    @Test
    void deleteClient() {
        int clientId = 1;
        Mockito.when(clientRepository.getAccountIdByClientId(clientId)).thenReturn(Optional.of(1));
        clientService.deleteClient(clientId);
        Mockito.verify(clientRepository, Mockito.times(1))
                .disableClientById(clientId);
        Mockito.verify(depositRepository, Mockito.times(1))
                .disableAllDepositsByClient(clientId);
    }

    @Test
    void getLegalForms() {
        clientService.getLegalForms();
        Mockito.verify(clientRepository, Mockito.times(1))
                .getLegalForms();
    }

    @Test
    void getLegalForm() {
        Assertions.assertThrows(ContentNotFoundException.class, () ->
                clientService.getLegalForm(1));
    }
}