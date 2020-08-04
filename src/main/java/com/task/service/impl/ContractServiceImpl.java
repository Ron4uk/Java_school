package com.task.service.impl;

import com.task.exception.NotExistClientException;
import com.task.dao.ClientDao;
import com.task.dao.ContractDao;
import com.task.dao.OptionDao;
import com.task.dao.TariffDao;
import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.dto.TariffDto;
import com.task.entity.*;
import com.task.service.ContractService;
import com.task.service.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ContractServiceImpl extends GenericMapper implements ContractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractServiceImpl.class);

    private ContractDao contractDAO;
    private TariffDao tariffDao;
    private OptionDao optionDao;
    private ClientDao clientDao;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Contract findByPhone(String phone) {

        return contractDAO.findByPhone(phone);
    }

    @Transactional
    @Override
    public void create(com.task.dto.ContractDto contractDto, com.task.dto.ClientDto clientDto, String tariffId, String[] optionsId) {
        check(contractDto.getPhone(), clientDto, contractDto);
        Tariff tariff = tariffDao.findById(Integer.parseInt(tariffId));
        Client client = (Client) convertToEntity(new Client(), clientDto);
        Contract contract = (Contract) convertToEntity(new Contract(), contractDto);
        contract.setClient(client);
        contract.setTariff(tariff);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.CLIENT);
        contract.getAuth().setRoles(roles);
        contract.getAuth().setPassword(passwordEncoder.encode(contract.getAuth().getPassword()));
        if (optionsId != null && optionsId.length > 0) {
            for (String stringId : optionsId) {
                Integer id = Integer.parseInt(stringId);
                Option option = optionDao.findById(id);

                contract.getConnectedOptions().add(option);
            }
        }

        LOGGER.info("[{}], create from ContractServiceImpl [{}]  client = {} and contract ={} options ={} tariff={}", LocalDateTime.now(), LOGGER.getName(), client, contract, contract.getConnectedOptions(), contract.getTariff());
        clientDao.create(client);
        contractDAO.create(contract);
    }

    @Transactional
    @Override
    public void check(String phone, com.task.dto.ClientDto clientDto, com.task.dto.ContractDto contractDto) {
        contractDAO.check(phone, clientDto, contractDto);
    }

    @Transactional
    @Override
    public ContractDto findByIdDto(String id) {
        Contract contract = contractDAO.findById(Integer.parseInt(id));
        ClientDto clientDto = (ClientDto) convertToDto(contract.getClient(), new ClientDto());
        TariffDto tariffDto = (TariffDto) convertToDto(contract.getTariff(), new TariffDto());
        ContractDto contractDto = (ContractDto) convertToDto(contract, new ContractDto());
        contractDto.setClientDto(clientDto);
        contractDto.setTariffDto(tariffDto);
        LOGGER.info("[{}], findByIdDto [{}] contractDto = {}", LocalDateTime.now(), LOGGER.getName(), contractDto);
        return contractDto;
    }

    @Transactional
    @Override
    public ContractDto findByPhoneDto(String phone, String path) {
        Contract contract = contractDAO.findByPhone(phone);
        LOGGER.info("[{}], [{}] findByPhone contract get client = {}", LocalDateTime.now(), LOGGER.getName(), contract);
        if (contract == null) {
            throw new NotExistClientException("The client doesn't exist", path);
        }
        ContractDto contractDto = (ContractDto) convertToDto(contract, new ContractDto());
        contractDto.setClientDto((ClientDto) convertToDto(contract.getClient(), new ClientDto()));
        contractDto.setTariffDto((TariffDto) convertToDto(contract.getTariff(), new TariffDto()));
        LOGGER.info("[{}], findByPhone [{}] contractDto = {}", LocalDateTime.now(), LOGGER.getName(), contractDto);
        return contractDto;
    }

    @Transactional
    @Override
    public ContractDto block(String id) {
        LOGGER.info("[{}], block [{}] id = {}", LocalDateTime.now(), LOGGER.getName(), id);
        Contract contract = contractDAO.findById(Integer.parseInt(id));
        contract.setBlockByOperator(true);
        LOGGER.info("block contract = {}", contract);
        contractDAO.update(contract);
        ClientDto clientDto = (ClientDto) convertToDto(contract.getClient(), new ClientDto());
        ContractDto contractDto = (ContractDto) convertToDto(contract, new ContractDto());
        contractDto.setClientDto(clientDto);
        contractDto.setTariffDto((TariffDto) convertToDto(contract.getTariff(), new TariffDto()));
        return contractDto;
    }

    @Transactional
    @Override
    public ContractDto unblock(String id) {
        LOGGER.info("[{}], unblock [{}] id = {}", LocalDateTime.now(), LOGGER.getName(), id);
        Contract contract = contractDAO.findById(Integer.parseInt(id));
        LOGGER.info("unblock contract = {}", contract);
        contract.setBlockByOperator(false);
        contract.setBlockByClient(false);
        contractDAO.update(contract);
        ClientDto clientDto = (ClientDto) convertToDto(contract.getClient(), new ClientDto());
        ContractDto contractDto = (ContractDto) convertToDto(contract, new ContractDto());
        contractDto.setClientDto(clientDto);
        contractDto.setTariffDto((TariffDto) convertToDto(contract.getTariff(), new TariffDto()));
        return contractDto;
    }

    @Transactional
    @Override
    public ContractDto update(ContractDto contractDto, String[] connectedOptions) {
        Contract contract = (Contract) convertToEntity(new Contract(), contractDto);
        Client client = (Client) convertToEntity(new Client(), contractDto.getClientDto());
        Tariff tariff = (Tariff) convertToEntity(new Tariff(), contractDto.getTariffDto());
        Authorization authorization = (Authorization) convertToEntity(new Authorization(), contractDto.getAuth());
        contract.setClient(client);
        contract.setTariff(tariff);
        contract.setAuth(authorization);
        LOGGER.info("[{}], update [{}] contract = {}", LocalDateTime.now(), LOGGER.getName(), contract);
        contract.setConnectedOptions(new HashSet<>());
        if (connectedOptions != null && connectedOptions.length > 0) {
            for (String id : connectedOptions) {
                Option option = optionDao.findById(Integer.parseInt(id));
                LOGGER.info("[{}], update [{}] option = {}", LocalDateTime.now(), LOGGER.getName(), option);
                contract.getConnectedOptions().add(option);
            }
        }
        LOGGER.info("[{}], update [{}] contract id = {}, auth id ={}, client id={}", LocalDateTime.now(), LOGGER.getName(), contract.getId(), contract.getAuth().getId(), contract.getClient().getId());

        Contract updatedContract = contractDAO.update(contract);
        ContractDto updatedContractDto = (ContractDto) convertToDto(updatedContract, new ContractDto());
        TariffDto updatedTariffDto = (TariffDto) convertToDto(updatedContract.getTariff(), new TariffDto());
        ClientDto updatedClientDto = (ClientDto) convertToDto(updatedContract.getClient(), new ClientDto());
        updatedContractDto.setTariffDto(updatedTariffDto);
        updatedContractDto.setClientDto(updatedClientDto);
        LOGGER.info("[{}], update [{}] updatedContractDto = {}", LocalDateTime.now(), LOGGER.getName(), updatedContractDto);

        return updatedContractDto;
    }

    @Transactional
    @Override
    public List<ContractDto> getAll() {
        LOGGER.info("[{}], getAll [{}] ", LocalDateTime.now(), LOGGER.getName());
        List<Contract> contracts = contractDAO.getAll();
        return contracts.stream().map(e ->
        {
            ContractDto contractDto = (ContractDto) convertToDto(e, new ContractDto());
            contractDto.setClientDto((ClientDto) convertToDto(e.getClient(), new ClientDto()));
            contractDto.setTariffDto((TariffDto) convertToDto(e.getTariff(), new TariffDto()));
            return contractDto;
        }).collect(Collectors.toList());
    }
}
