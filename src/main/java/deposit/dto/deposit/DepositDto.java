package deposit.dto.deposit;

import deposit.entity.Deposit;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class DepositDto {
    @Min(1)
    private int id;
    @Min(1)
    private int clientId;
    @Min(1)
    private int bankId;
    private Timestamp creationDate;
    private float percent;
    private short monthPeriod;

    public DepositDto(Deposit deposit) {
        this.id = deposit.getId();
        this.clientId = deposit.getClientId();
        this.bankId = deposit.getBankId();
        this.creationDate = deposit.getCreationDate();
        this.percent = deposit.getPercent();
        this.monthPeriod = deposit.getMonthPeriod();
    }
}
