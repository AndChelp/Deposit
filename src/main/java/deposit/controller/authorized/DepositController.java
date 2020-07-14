package deposit.controller.authorized;

import deposit.dto.deposit.DepositCreationDto;
import deposit.dto.deposit.DepositDto;
import deposit.dto.utils.PageDto;
import deposit.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deposits")
public class DepositController {
    @Autowired
    private DepositService depositService;

    @GetMapping
    public ResponseEntity<PageDto> getAllDeposits(
            @RequestParam(required = false, name = "client_id") Integer clientId,
            @RequestParam(required = false, name = "bank_id") Integer bankId,
            @RequestParam(required = false, name = "percent") Integer percent,
            @RequestParam(required = false, name = "month_period") Integer monthPeriod,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        return new ResponseEntity<>(
                depositService.getDeposits(clientId, bankId, percent, monthPeriod, page, size, sort),
                HttpStatus.OK);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addDeposit(@RequestBody DepositCreationDto depositCreationDto) {
        depositService.addDeposit(depositCreationDto);
    }

    @PutMapping
    public void updateDeposit(@RequestBody DepositDto depositDto) {
        depositService.updateDeposit(depositDto);
    }

    @DeleteMapping
    public void deleteDeposit(@RequestParam(name = "deposit_id") int depositId) {
        depositService.deleteDeposit(depositId);
    }
}
