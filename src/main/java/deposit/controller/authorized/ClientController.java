package deposit.controller.authorized;

import deposit.dto.client.ClientCreationDto;
import deposit.dto.client.ClientDto;
import deposit.dto.utils.PageDto;
import deposit.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<PageDto> getAllClients(
            @RequestParam(required = false, name = "name_contains") String nameContains,
            @RequestParam(required = false, name = "bik_contains") String bikContains,
            @RequestParam(required = false, name = "legal_form") Integer legalForm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        return new ResponseEntity<>(
                clientService.getClients(nameContains, bikContains, legalForm, page, size, sort),
                HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addClient(@RequestBody ClientCreationDto clientCreationDto) {
        clientService.addClient(clientCreationDto);
    }

    @PutMapping
    public void updateClient(@RequestBody ClientDto clientDto) {
        clientService.updateClient(clientDto);
    }

    @DeleteMapping
    public void deleteClient(@RequestParam(name = "client_id") int clientId) {
        clientService.deleteClient(clientId);
    }

}




























