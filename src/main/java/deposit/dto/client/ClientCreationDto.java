package deposit.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreationDto {
    @Length(min = 1, max = 100)
    private String name;
    @Length(min = 1, max = 100)
    private String shortName;
    private AddressDto address;
    @Min(1)
    private int legalFormId;
}
