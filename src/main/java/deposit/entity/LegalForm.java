package deposit.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Организационно-правовая форма.
 * Полям невозможно задать новое значение, т.к. список всех актуальных ОПФ хранится в базе
 */
@Getter
@Entity
@Table(name = "legal_forms")
public class LegalForm {
    @Id
    private int id;

    @Column(name = "name", nullable = false, length = 400)
    private String name;
}
