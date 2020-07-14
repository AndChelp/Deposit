package deposit.dto.bank;

import deposit.validation.annotation.Bik;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankCreationDto {
    @Length(min = 2, max = 100)
    private String name;
    @Bik
    private String bik;
}
