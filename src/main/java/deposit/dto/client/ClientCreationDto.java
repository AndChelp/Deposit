package deposit.dto.client;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClientCreationDto {
    private String name;
    private String shortName;
    private AddressDto address;
    private int legalFormId;
}
