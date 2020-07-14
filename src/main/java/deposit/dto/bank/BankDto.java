package deposit.dto.bank;

import deposit.entity.Bank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BankDto {
    private int id;
    private String name;
    private String bik;

    public BankDto(Bank bank) {
        this.id = bank.getId();
        this.name = bank.getName();
        this.bik = bank.getBik();
    }
}
