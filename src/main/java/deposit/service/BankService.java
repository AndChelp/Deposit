package deposit.service;

import deposit.dto.bank.BankCreationDto;
import deposit.dto.bank.BankDto;
import deposit.dto.utils.PageDto;
import deposit.entity.Bank;
import deposit.exception.ContentNotFoundException;
import deposit.exception.PermissionDeniedException;
import deposit.repository.BankRepository;
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
 * Сервисный класс для работы с банками.
 *
 * Позволяет получить отфильтрованный и/или отсортированный список банков с помощью Criteria API,
 * добавить новый банк, обновить и удалить существующий.
 */

@Service
public class BankService {
    private final BankRepository bankRepository;
    private final EntityManager entityManager;

    @Autowired
    public BankService(BankRepository bankRepository, EntityManager entityManager) {
        this.bankRepository = bankRepository;
        this.entityManager = entityManager;
    }

    public PageDto getBanks(String nameContains, String bikContains, int pageNumber, int pageSize, String[] sort) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bank> criteriaQuery = criteriaBuilder.createQuery(Bank.class);
        Root<Bank> entity = criteriaQuery.from(Bank.class);
        criteriaQuery.select(entity);

        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(criteriaBuilder.equal(entity.get("accountId"), getCurrentUserId()));
        predicateList.add(criteriaBuilder.equal(entity.get("active"), true));
        if (nameContains != null)
            predicateList.add(criteriaBuilder.like(entity.get("name"), "%" + nameContains + "%"));
        if (bikContains != null)
            predicateList.add(criteriaBuilder.like(entity.get("bik"), "%" + bikContains + "%"));

        criteriaQuery.where(predicateList.toArray(new Predicate[0]));
        criteriaQuery.orderBy(getOrderList(sort, criteriaBuilder, entity));

        TypedQuery<Bank> query = entityManager.createQuery(criteriaQuery);
        int matchingResultsCount = query.getResultList().size();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        List<BankDto> bankDtoList = query.getResultList()
                .stream()
                .map(BankDto::new)
                .collect(Collectors.toList());

        if (bankDtoList.isEmpty())
            throw new ContentNotFoundException();

        return PageDto.builder()
                .currentPage(pageNumber)
                .totalPages((matchingResultsCount + pageSize - 1) / pageSize)
                .items(bankDtoList)
                .build();
    }

    public void addBank(BankCreationDto bankCreationDto) {
        Bank bank = new Bank();
        bank.setName(bankCreationDto.getName());
        bank.setBik(bankCreationDto.getBik());
        bank.setAccountId(getCurrentUserId());
        bankRepository.save(bank);
    }

    public void updateBank(BankDto bankDto) {
        Bank bank = bankRepository.findById(bankDto.getId())
                .orElseThrow(ContentNotFoundException::new);
        if (bank.getAccountId() != getCurrentUserId())
            throw new PermissionDeniedException();
        bank.setName(bankDto.getName());
        bank.setBik(bankDto.getBik());
        bankRepository.save(bank);
    }


    public void deleteBank(int bankId) {
        int ownerId = bankRepository.getAccountIdByBankId(bankId)
                .orElseThrow(ContentNotFoundException::new);
        if (ownerId != getCurrentUserId())
            throw new PermissionDeniedException();
        bankRepository.disableBankById(bankId);
    }

}























