package deposit.dto.deposit;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
public class DepositCreationDto {
    @Min(1)
    private int clientId;
    @Min(1)
    private int bankId;
    private float percent;
    private short monthPeriod;
}
