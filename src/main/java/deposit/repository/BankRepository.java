package deposit.repository;

import deposit.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Bank u SET active = false WHERE u.id = :bankId")
    void disableBankById(int bankId);

    @Query("SELECT u.accountId FROM Bank u WHERE u.id = :bankId")
    Optional<Integer> getAccountIdByBankId(int bankId);
}
