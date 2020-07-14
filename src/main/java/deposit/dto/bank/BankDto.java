package deposit.dto.bank;

import deposit.entity.Bank;
import deposit.validation.annotation.Bik;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
public class BankDto {
    @Min(0)
    private int id;
    @Length(min = 2, max = 100)
    private String name;
    @Bik
    private String bik;

    public BankDto(Bank bank) {
        this.id = bank.getId();
        this.name = bank.getName();
        this.bik = bank.getBik();
    }
}
