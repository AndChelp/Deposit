package deposit.controller.authorized;

import deposit.dto.client.ClientCreationDto;
import deposit.dto.client.ClientDto;
import deposit.dto.utils.PageDto;
import deposit.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для взаимодействия с клиентами.
 *
 * Доступные запросы:
 * [GET]    /api/clients?name_contains          — non required, String, поиск по наименованию или его части
 *                      &short_name_contains    — non required, String, поиск по краткому наименованию или его части
 *                      &legal_form_id          — non required, Integer, поиск по id ОПФ
 *                      &page                   — non required, Integer, номер страницы, по умолчанию 0
 *                      &size                   — non required, Integer, размер страницы, по умолчанию 5
 *                      &sort                   — non required, String[], сортировка по полю(-ям), по умолчанию id,desc
 * [POST]   /api/clients (body){ClientCreationDto}
 * [PUT]    /api/clients (body){ClientDto}
 * [DELETE] /api/clients?client_id              — required, Integer, id клиента
 */

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<PageDto> getAllClients(
            @RequestParam(required = false, name = "name_contains") String nameContains,
            @RequestParam(required = false, name = "short_name_contains") String shortNameContains,
            @RequestParam(required = false, name = "legal_form_id") Integer legalFormId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        return new ResponseEntity<>(
                clientService.getClients(nameContains, shortNameContains, legalFormId, page, size, sort),
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




























