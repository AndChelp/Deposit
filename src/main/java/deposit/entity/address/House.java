package deposit.entity.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Дом.
 * Ограничение в 20 символов связано с отсутствием реализации понятий офисов, корпусов, подъездов, квартир и т.п.
 */

@Getter
@NoArgsConstructor
@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Length(min = 1, max = 20, message = "Допустимая длина номера дома от 1 до 20 символов")
    @Column(name = "house_number", nullable = false, length = 400)
    private String houseNumber;

    public House(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
