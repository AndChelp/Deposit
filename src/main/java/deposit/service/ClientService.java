package deposit.service;

import deposit.dto.client.AddressDto;
import deposit.dto.client.ClientCreationDto;
import deposit.dto.client.ClientDto;
import deposit.dto.utils.LegalFormDto;
import deposit.dto.utils.PageDto;
import deposit.entity.Client;
import deposit.entity.address.*;
import deposit.exception.ContentNotFoundException;
import deposit.exception.PermissionDeniedException;
import deposit.repository.ClientRepository;
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

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final DepositRepository depositRepository;
    private final EntityManager entityManager;

    @Autowired
    public ClientService(ClientRepository clientRepository, DepositRepository depositRepository, EntityManager entityManager) {
        this.clientRepository = clientRepository;
        this.depositRepository = depositRepository;
        this.entityManager = entityManager;
    }

    public PageDto getClients(String nameContains, String shortNameContains, Integer legalForm, int pageNumber, int pageSize, String[] sort) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> entity = criteriaQuery.from(Client.class);
        criteriaQuery.select(entity);

        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(criteriaBuilder.equal(entity.get("accountId"), getCurrentUserId()));
        predicateList.add(criteriaBuilder.equal(entity.get("active"), true));
        if (nameContains != null)
            predicateList.add(criteriaBuilder.like(entity.get("name"), "%" + nameContains + "%"));
        if (shortNameContains != null)
            predicateList.add(criteriaBuilder.like(entity.get("shortName"), "%" + shortNameContains + "%"));
        if (legalForm != null)
            predicateList.add(criteriaBuilder.equal(entity.get("legalForm"), legalForm));

        criteriaQuery.where(predicateList.toArray(new Predicate[0]));
        criteriaQuery.orderBy(getOrderList(sort, criteriaBuilder, entity));

        TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
        int matchingResultsCount = query.getResultList().size();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        List<ClientDto> clientDtoList = query.getResultList()
                .stream()
                .map(ClientDto::new)
                .collect(Collectors.toList());

        if (clientDtoList.isEmpty())
            throw new ContentNotFoundException();

        return PageDto.builder()
                .currentPage(pageNumber)
                .totalPages((matchingResultsCount + pageSize - 1) / pageSize)
                .items(clientDtoList)
                .build();
    }

    public void addClient(ClientCreationDto clientCreationDto) {
        Client client = new Client();
        client.setName(clientCreationDto.getName());
        client.setShortName(clientCreationDto.getShortName());
        client.setAddress(createAddress(clientCreationDto.getAddress()));
        client.setLegalFormId(clientCreationDto.getLegalFormId());
        client.setAccountId(getCurrentUserId());
        clientRepository.save(client);
    }

    public void updateClient(ClientDto clientDto) {
        Client client = clientRepository.findById(clientDto.getId())
                .orElseThrow(ContentNotFoundException::new);
        if (client.getAccountId() != getCurrentUserId())
            throw new PermissionDeniedException();
        client.setAddress(createAddress(clientDto.getAddress()));
        client.setLegalFormId(clientDto.getLegalFormId());
        client.setName(clientDto.getName());
        client.setShortName(clientDto.getShortName());
        clientRepository.save(client);
    }

    public void deleteClient(int clientId) {
        int ownerId = clientRepository.getAccountIdByClientId(clientId)
                .orElseThrow(ContentNotFoundException::new);
        if (ownerId != getCurrentUserId())
            throw new PermissionDeniedException();
        clientRepository.disableClientById(clientId);
        depositRepository.disableAllDepositsByClient(clientId);
    }

    public List<LegalFormDto> getLegalForms() {
        return clientRepository.getLegalForms();
    }

    public LegalFormDto getLegalForm(int id) {
        return clientRepository.getLegalFormById(id)
                .orElseThrow(ContentNotFoundException::new);
    }

    private Address createAddress(AddressDto addressDto) {
        Index index = clientRepository.findIndex(addressDto.getIndex())
                .orElse(new Index(addressDto.getIndex()));
        City city = clientRepository.findCity(addressDto.getCity())
                .orElse(new City(addressDto.getCity()));
        Street street = clientRepository.findStreet(addressDto.getStreet())
                .orElse(new Street(addressDto.getStreet()));
        House house = clientRepository.findHouse(addressDto.getHouse())
                .orElse(new House(addressDto.getHouse()));
        return new Address(index, city, street, house);
    }
}

























