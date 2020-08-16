package com.task.service.impl;

import com.task.dao.ClientDao;
import com.task.dto.ClientDto;
import com.task.dto.DtoEntity;
import com.task.entity.Client;
import com.task.entity.Contract;
import com.task.service.ClientService;
import com.task.service.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@AllArgsConstructor(onConstructor=@__({@Autowired}))
public class ClientServiceImpl extends GenericMapper implements ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);
    private static final int NUMBER_CONTRACTS_ON_PAGE = 5;

    private ClientDao clientDao;

    @Transactional
    public List<Client> getAll() {
        LOGGER.info("[{}]  Connect to DB from method getAll {}", LocalDateTime.now(), LOGGER.getName());
        return clientDao.getAll();
    }
    @Transactional
    public List<DtoEntity> getAllDto() {
        return clientDao.getAll().stream().map(e->this.convertToDto(e, new ClientDto())).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public DtoEntity update(ClientDto clientDto) {
        Client client = (Client) convertToEntity(new Client(), clientDto);
        LOGGER.info("[{}]  save/update client  {}", LocalDateTime.now(), client);
        DtoEntity dtoEntity= convertToDto(clientDao.update(client), new com.task.dto.ClientDto());
        LOGGER.info("[{}]  save/update client id = {}", LocalDateTime.now(), client.getId());
        return dtoEntity;
    }
    @Transactional
    @Override
    public void check(ClientDto clientDto) {
        clientDao.check(clientDto);
    }
    @Transactional
    @Override
    public ClientDto findByIdDto(String id) {
        Client client = clientDao.findById(Integer.parseInt(id));
        return (ClientDto)convertToDto(client, new ClientDto());
    }

    @Transactional
    @Override
    public List<ClientDto> getAllDtoByPage(Integer id) {
        int skipClients = id != null && id > 0 ? (id - 1) * NUMBER_CONTRACTS_ON_PAGE : 0;
        List<Client> clients = clientDao.getAllByPage(skipClients, NUMBER_CONTRACTS_ON_PAGE);
        return clients.stream().map(e->(ClientDto) this.convertToDto(e, new ClientDto())).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Long countContractsInBd() {
        Long count = (long) (Math.ceil(((double) clientDao.countContractsInBd()) / NUMBER_CONTRACTS_ON_PAGE));
        return count;
    }
}
