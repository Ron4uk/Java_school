package com.task.service.implementation;

import com.task.dao.ClientDao;
import com.task.dto.DtoEntity;
import com.task.entity.Client;
import com.task.service.ClientService;
import com.task.service.GenericMapper;
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
@Transactional
@NoArgsConstructor
public class ClientServiceImpl extends GenericMapper implements ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);
    @Getter
    @Setter (onMethod =@__({@Autowired}))
    private ClientDao clientDao;


    public List<Client> getAll() {
        LOGGER.info("[{}]  Connect to DB from method getAll {}", LocalDateTime.now(), LOGGER.getName());
        return clientDao.getAll();
    }

    public List<DtoEntity> getAllDto() {

        return clientDao.getAll().stream().map(e->this.convertToDto(e, new com.task.dto.ClientDto())).collect(Collectors.toList());
    }

    @Override
    public DtoEntity update(com.task.dto.ClientDto clientDto) {
        Client client = (Client) convertToEntity(new Client(), clientDto);
        LOGGER.info("[{}]  save/update client  {}", LocalDateTime.now(), client);
        DtoEntity dtoEntity= convertToDto(clientDao.update(client), new com.task.dto.ClientDto());
        LOGGER.info("[{}]  save/update client id = {}", LocalDateTime.now(), client.getId());
        return dtoEntity;
    }

    @Override
    public void check(com.task.dto.ClientDto clientDto) {
        clientDao.check(clientDto);
    }
}
