package deposit.entity.address;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Адрес.
 * Включает в себя индекс, город, улицу и дом.
 */

@Getter
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_index")
    private Index index;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_city")
    private City city;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_street")
    private Street street;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_house")
    private House house;

    public Address(Index index, City city, Street street, House house) {
        this.index = index;
        this.city = city;
        this.street = street;
        this.house = house;
    }
}
