package deposit.entity.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Улица.
 * Максимальная длина ограничена соответствующим названием.
 */

@Getter
@NoArgsConstructor
@Entity
@Table(name = "streets")
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Length(min = 2, max = 150, message = "Допустимая длина названия улицы от 3 до 150 символов")
    @Column(name = "street_name", nullable = false, length = 400)
    private String streetName;

    public Street(String streetName) {
        this.streetName = streetName;
    }
}
