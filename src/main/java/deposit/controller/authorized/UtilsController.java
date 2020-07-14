package deposit.controller.authorized;

import deposit.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для вспомогательных операций.
 *
 * Доступные запросы:
 * /api/utils/legal_forms?id    — non required, Integer, поиск по id ОПФ
 */

@RestController
@RequestMapping("/api/utils")
public class UtilsController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/legal_forms")
    public ResponseEntity<?> getLegalForms(@RequestParam(required = false) Integer id) {

        if (id == null) //Если id не задан, вернуть все ОПФ
            return new ResponseEntity<>(
                    clientService.getLegalForms(),
                    HttpStatus.OK);
        else //Иначе вернуть требуемую ОПФ
            return new ResponseEntity<>(
                    clientService.getLegalForm(id),
                    HttpStatus.OK);
    }

}
