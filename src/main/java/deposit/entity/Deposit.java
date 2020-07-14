package deposit.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "deposits")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_client")
    private int clientId;

    @Column(name = "id_bank")
    private int bankId;

    @Column(name = "id_account", nullable = false)
    private int accountId;

    @Column(name = "creation_date", nullable = false)
    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

    @Column(name = "percent", nullable = false)
    private float percent;

    @Column(name = "month_period", nullable = false)
    private short monthPeriod;

    @Column(name = "active", nullable = false)
    private boolean active = true;
}
