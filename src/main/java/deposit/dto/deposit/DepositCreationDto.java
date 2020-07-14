package deposit.dto.deposit;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepositCreationDto {
    private int clientId;
    private int bankId;
    private float percent;
    private short monthPeriod;
}
