package deposit.repository;

import deposit.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Deposit u SET active = false WHERE u.clientId = :clientId")
    void disableAllDepositsByClient(int clientId);

    @Transactional
    @Modifying
    @Query("UPDATE Deposit u SET active = false WHERE u.id = :depositId")
    void disableDepositById(int depositId);

    @Query("SELECT u.accountId FROM Deposit u WHERE u.id = :depositId")
    Optional<Integer> getAccountByDepositId(int depositId);

}
