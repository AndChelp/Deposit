package deposit.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Аккаунты.
 * Если использовались команды Insert из скрипта БД, то тогда по умолчанию определены два аккаунта:
 * <p>
 * login: firstUser
 * password: firstPass
 * <p>
 * login: secondUser
 * password: secondPass
 * <p>
 * За каждым закреплены несколько клиентов с несколькими вкладами.
 */
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login", nullable = false, length = 400)
    private String username;

    @Column(name = "email", nullable = false, length = 400)
    private String email;

    @Column(name = "password", nullable = false, length = 400)
    private String password;

    private boolean active = true;

}
