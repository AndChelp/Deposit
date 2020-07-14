package deposit.entity.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Город.
 * Максимальная длина ограничена городом Александровск-Сахалинский(25 символов).
 * В данной реализации позволено указать произвольный город, в идеале необходимо подключить ФИАС или подобные сервисы и
 * давать города на выбор. Аналогично с улицами, домами и индексами.
 */

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Length(min = 2, max = 25, message = "Допустимая длина названия города от 2 до 25 символов")
    @Column(name = "city_name", nullable = false, length = 400)
    private String cityName;

    public City(String cityName) {
        this.cityName = cityName;
    }
}
