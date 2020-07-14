package deposit.controller.authorized;

import deposit.dto.bank.BankCreationDto;
import deposit.dto.bank.BankDto;
import deposit.dto.utils.PageDto;
import deposit.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banks")
public class BankController {
    @Autowired
    private BankService bankService;

    @GetMapping
    public ResponseEntity<PageDto> getAllBanks(
            @RequestParam(required = false, name = "name_contains") String nameContains,
            @RequestParam(required = false, name = "bik_contains") String bik_contains,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        return new ResponseEntity<>(
                bankService.getBanks(nameContains, bik_contains, page, size, sort),
                HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addBank(@RequestBody BankCreationDto bankCreationDto) {
        bankService.addBank(bankCreationDto);
    }

    @PutMapping
    public void updateBank(@RequestBody BankDto bankDto) {
        bankService.updateBank(bankDto);
    }

    @DeleteMapping
    public void deleteBank(@RequestParam(name = "bank_id") int bankId) {
        bankService.deleteBank(bankId);
    }

}
