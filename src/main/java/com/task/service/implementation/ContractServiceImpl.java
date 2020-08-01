package com.task.service.implementation;

import com.task.customeexceptions.NotExistClientException;
import com.task.dao.ClientDao;
import com.task.dao.ContractDao;
import com.task.dao.OptionDao;
import com.task.dao.TariffDao;
import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.entity.*;
import com.task.service.ContractService;
import com.task.service.GenericMapper;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class ContractServiceImpl extends GenericMapper implements ContractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractServiceImpl.class);
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ContractDao contractDAO;
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TariffDao tariffDao;
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private OptionDao optionDao;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ClientDao clientDao;

    public Contract findByPhone(String phone) {

        return contractDAO.findByPhone(phone);
    }

    @Override
    public void create(com.task.dto.ContractDto contractDto, com.task.dto.ClientDto clientDto, String tariffId, String[] optionsId) {
        check(contractDto.getPhone(), clientDto, contractDto);
        Tariff tariff = tariffDao.findById(Integer.parseInt(tariffId));
        Client client =(Client) convertToEntity(new Client(), clientDto);
        Contract contract = (Contract) convertToEntity(new Contract(), contractDto);
        contract.setClient(client);
        contract.setTariff(tariff);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.CLIENT);
        contract.getAuth().setRoles(roles);
        if(optionsId!=null && optionsId.length>0){
            for(String stringId :optionsId){
                Integer id = Integer.parseInt(stringId);
                Option option = optionDao.findById(id);

                contract.getConnectedOptions().add(option);
            }
        }

        LOGGER.info("[{}], create from ContractServiceImpl [{}]  client = {} and contract ={} options ={} tariff={}", LocalDateTime.now(), LOGGER.getName(), client, contract, contract.getConnectedOptions(), contract.getTariff());
        clientDao.create(client);
        contractDAO.create(contract);
    }
    @Override
    public void check(String phone, com.task.dto.ClientDto clientDto, com.task.dto.ContractDto contractDto) {
        contractDAO.check(phone, clientDto, contractDto);
    }


    @Override
    public ClientDto findByIdDto(String id) {
        Client client = clientDao.findById(Integer.parseInt(id));
        return (ClientDto)convertToDto(client, new ClientDto());
    }

    @Override
    public ContractDto findByPhoneDto(String phone, String path) {
        Contract contract = contractDAO.findByPhone(phone);
        LOGGER.info("[{}], [{}] findByPhone contract get client = {}", LocalDateTime.now(), LOGGER.getName(), contract);
        if(contract==null) {
            throw new NotExistClientException("The client doesn't exist", path);
        }
        ContractDto contractDto =(ContractDto) convertToDto(contract, new ContractDto());
        contractDto.setClientDto((ClientDto) convertToDto(contract.getClient(), new ClientDto()));
        LOGGER.info("[{}], findByPhone [{}] contractDto = {}", LocalDateTime.now(), LOGGER.getName(), contractDto);
        return contractDto;
    }

    @Override
    public ContractDto block(String id) {
        LOGGER.info("[{}], block [{}] id = {}", LocalDateTime.now(), LOGGER.getName(), id);
        Contract contract= contractDAO.findById(Integer.parseInt(id));
        contract.setBlockByOperator(true);
        LOGGER.info("update contract = {}", contract);
        contractDAO.update(contract);
        ClientDto clientDto = (ClientDto) convertToDto(contract.getClient(), new ClientDto());
        ContractDto contractDto = (ContractDto) convertToDto(contract, new ContractDto());
        contractDto.setClientDto(clientDto);
        return contractDto;
    }

    @Override
    public ContractDto unblock(String id) {
        LOGGER.info("[{}], unblock [{}] id = {}", LocalDateTime.now(), LOGGER.getName(), id);
        Contract contract= contractDAO.findById(Integer.parseInt(id));
        LOGGER.info("update contract = {}", contract);
        contract.setBlockByOperator(false);
        contractDAO.update(contract);
        ClientDto clientDto = (ClientDto) convertToDto(contract.getClient(), new ClientDto());
        ContractDto contractDto = (ContractDto) convertToDto(contract, new ContractDto());
        contractDto.setClientDto(clientDto);
        return contractDto;
    }
}
