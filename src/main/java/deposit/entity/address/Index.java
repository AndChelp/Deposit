package deposit.entity.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Индекс.
 */

@Getter
@NoArgsConstructor
@Entity
@Table(name = "indexes")
public class Index {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Length(min = 6, max = 6, message = "Длина индекса должна составлять 6 символов")
    @Column(name = "index_value", nullable = false, length = 6)
    private String indexValue;

    public Index(String indexValue) {
        this.indexValue = indexValue;
    }

}
