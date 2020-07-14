package deposit.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_account", nullable = false)
    private int accountId;

    @Length(min = 2, max = 400, message = "Допустимая длина названия банка от 2 до 400 символов")
    @Column(name = "name", nullable = false, length = 400)
    private String name;

    @Length(min = 9, max = 9, message = "Длина БИК должна составлять 9 символов")
    @Column(name = "bik", nullable = false, length = 9)
    private String bik;

    @Column(name = "active", nullable = false)
    private boolean active = true;
}
