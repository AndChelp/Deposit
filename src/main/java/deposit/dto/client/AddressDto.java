package deposit.dto.client;

import deposit.entity.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    @Length(min=6, max = 6)
    private String index;
    @Length(min=2, max = 25)
    private String city;
    @Length(min=2, max = 150)
    private String street;
    @Length(min=1, max = 20)
    private String house;

    public AddressDto(Address address) {
        this.index = address.getIndex().getIndexValue();
        this.city = address.getCity().getCityName();
        this.street = address.getStreet().getStreetName();
        this.house = address.getHouse().getHouseNumber();
    }
}
