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

    @Column(name = "name", nullable = false, length = 400)
    private String name;

    @Column(name = "bik", nullable = false, length = 9)
    private String bik;

    @Column(name = "active", nullable = false)
    private boolean active = true;
}
