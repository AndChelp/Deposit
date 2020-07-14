package deposit.repository;

import deposit.dto.utils.LegalFormDto;
import deposit.entity.Client;
import deposit.entity.address.City;
import deposit.entity.address.House;
import deposit.entity.address.Index;
import deposit.entity.address.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Client u SET active = false WHERE u.id = :clientId")
    void disableClientById(int clientId);

    @Query("SELECT u.accountId FROM Client u WHERE u.id = :clientId")
    Optional<Integer> getAccountIdByClientId(int clientId);

    @Query("SELECT new deposit.dto.utils.LegalFormDto(u.id,u.name) FROM LegalForm u")
    List<LegalFormDto> getLegalForms();

    @Query("SELECT new deposit.dto.utils.LegalFormDto(u.id,u.name) FROM LegalForm u WHERE u.id = :id")
    Optional<LegalFormDto> getLegalFormById(int id);

    @Query("SELECT u FROM Index u WHERE u.indexValue = :indexValue")
    Optional<Index> findIndex(String indexValue);

    @Query("SELECT u FROM City u WHERE u.cityName = :cityName")
    Optional<City> findCity(String cityName);

    @Query("SELECT u FROM Street u WHERE u.streetName = :streetName")
    Optional<Street> findStreet(String streetName);

    @Query("SELECT u FROM House u WHERE u.houseNumber = :houseNumber")
    Optional<House> findHouse(String houseNumber);
}



























