package deposit.dto.client;

import deposit.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    @Min(0)
    private int id;
    @Length(min = 1, max = 100)
    private String name;
    @Length(min = 1, max = 100)
    private String shortName;
    private AddressDto address;
    @Min(1)
    private int legalFormId;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.shortName = client.getShortName();
        this.address = new AddressDto(client.getAddress());
        this.legalFormId = client.getLegalFormId();
    }
}
