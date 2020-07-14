package deposit.dto.client;

import deposit.entity.address.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressDto {
    private String index;
    private String city;
    private String street;
    private String house;

    public AddressDto(Address address) {
        this.index = address.getIndex().getIndexValue();
        this.city = address.getCity().getCityName();
        this.street = address.getStreet().getStreetName();
        this.house = address.getHouse().getHouseNumber();
    }
}
