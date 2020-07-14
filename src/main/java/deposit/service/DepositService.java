package deposit.service;

import deposit.dto.deposit.DepositCreationDto;
import deposit.dto.deposit.DepositDto;
import deposit.dto.utils.PageDto;
import deposit.entity.Deposit;
import deposit.exception.ContentNotFoundException;
import deposit.exception.PermissionDeniedException;
import deposit.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static deposit.service.Utils.getCurrentUserId;
import static deposit.service.Utils.getOrderList;

/**
 * Сервисный класс для работы со вкладами.
 *
 * Позволяет получить отфильтрованный и/или отсортированный список клиентов с помощью Criteria API,
 * добавить новый вклад, обновить и удалить существующий.
 */

@Service
public class DepositService {
    private final EntityManager entityManager;
    private final DepositRepository depositRepository;

    @Autowired
    public DepositService(EntityManager entityManager, DepositRepository depositRepository) {
        this.entityManager = entityManager;
        this.depositRepository = depositRepository;
    }

    public PageDto getDeposits(Integer clientId, Integer bankId, Integer percent, Integer monthPeriod,
                               int pageNumber, int pageSize,
                               String[] sort) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Deposit> criteriaQuery = criteriaBuilder.createQuery(Deposit.class);
        Root<Deposit> entity = criteriaQuery.from(Deposit.class);
        criteriaQuery.select(entity);

        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(criteriaBuilder.equal(entity.get("accountId"), getCurrentUserId()));
        predicateList.add(criteriaBuilder.equal(entity.get("active"), true));
        if (clientId != null)
            predicateList.add(criteriaBuilder.equal(entity.get("clientId"), clientId));
        if (bankId != null)
            predicateList.add(criteriaBuilder.equal(entity.get("bankId"), bankId));
        if (percent != null)
            predicateList.add(criteriaBuilder.equal(entity.get("percent"), percent));
        if (monthPeriod != null)
            predicateList.add(criteriaBuilder.equal(entity.get("monthPeriod"), monthPeriod));

        criteriaQuery.where(predicateList.toArray(new Predicate[0]));
        criteriaQuery.orderBy(getOrderList(sort, criteriaBuilder, entity));

        TypedQuery<Deposit> query = entityManager.createQuery(criteriaQuery);
        int matchingResultsCount = query.getResultList().size();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        List<DepositDto> depositDtoList = query.getResultList()
                .stream()
                .map(DepositDto::new)
                .collect(Collectors.toList());

        if (depositDtoList.isEmpty())
            throw new ContentNotFoundException();

        return PageDto.builder()
                .currentPage(pageNumber)
                .totalPages((matchingResultsCount + pageSize - 1) / pageSize)
                .items(depositDtoList)
                .build();
    }

    public void addDeposit(DepositCreationDto depositCreationDto) {
        Deposit deposit = new Deposit();
        deposit.setAccountId(getCurrentUserId());
        deposit.setClientId(depositCreationDto.getClientId());
        deposit.setBankId(depositCreationDto.getBankId());
        deposit.setPercent(depositCreationDto.getPercent());
        deposit.setMonthPeriod(depositCreationDto.getMonthPeriod());
        depositRepository.save(deposit);
    }

    public void updateDeposit(DepositDto depositDto) {
        Deposit deposit = depositRepository.findById(depositDto.getId())
                .orElseThrow(ContentNotFoundException::new);
        if (deposit.getAccountId() != getCurrentUserId())
            throw new PermissionDeniedException();
        deposit.setClientId(depositDto.getClientId());
        deposit.setBankId(depositDto.getBankId());
        deposit.setCreationDate(depositDto.getCreationDate());
        deposit.setPercent(depositDto.getPercent());
        deposit.setMonthPeriod(depositDto.getMonthPeriod());
        depositRepository.save(deposit);
    }

    public void deleteDeposit(int depositId) {
        int ownerId = depositRepository.getAccountByDepositId(depositId)
                .orElseThrow(ContentNotFoundException::new);
        if (ownerId != getCurrentUserId())
            throw new PermissionDeniedException();
        depositRepository.disableDepositById(depositId);
    }
}
































