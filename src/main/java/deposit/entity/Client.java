package deposit.entity;

import deposit.entity.address.Address;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Клиенты.
 * Адрес и ОПФ вынесены в отдельные сущности для нормализации.
 * Добавлены аккаунты для функционала авторизации в систему.
 */

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    private Address address;

    @Column(name = "id_account", nullable = false)
    private int accountId;

    @Column(name = "id_legal_form", nullable = false)
    private int legalFormId;

    @Length(min = 1, max = 400, message = "Допустимая длина наименования от 1 до 400 символов")
    @Column(name = "name", nullable = false, length = 400)
    private String name;

    @Length(min = 1, max = 400, message = "Допустимая длина сокращенного наименования от 1 до 400 символов")
    @Column(name = "short_name", nullable = false, length = 400)
    private String shortName;

    @Column(name = "active", nullable = false)
    private boolean active = true;
}

