package deposit.dto.client;

import deposit.entity.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClientDto {
    private int id;
    private String name;
    private String shortName;
    private AddressDto address;
    private int legalFormId;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.shortName = client.getShortName();
        this.address = new AddressDto(client.getAddress());
        this.legalFormId = client.getLegalFormId();
    }
}
