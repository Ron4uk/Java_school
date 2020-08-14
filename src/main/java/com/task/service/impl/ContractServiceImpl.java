package com.task.service.impl;

import com.task.dto.*;
import com.task.exception.NotExistClientException;
import com.task.dao.ClientDao;
import com.task.dao.ContractDao;
import com.task.dao.OptionDao;
import com.task.dao.TariffDao;
import com.task.entity.*;
import com.task.service.ContractService;
import com.task.service.GenericMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
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
@Log4j
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
    public String create(ContractDto contractDto, ClientDto clientDto, String tariffId, String[] optionsId) {

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
        if (!checkOptions(contract.getConnectedOptions())) return "Change failed. Check connection options";

        if (clientDto.getId() == null) {
            clientDao.create(client);
        }
        contractDAO.create(contract);
        return "New contract added.";
    }

    private boolean checkOptions(Set<Option> connectedOptions) {
        for (Option option : connectedOptions) {
            if (option.getRequiredOptions().size() > 0) {
                for (Option required : option.getRequiredOptions()) {
                    if (!connectedOptions.contains(required)) return false;
                }
            }
            if (option.getExclusionOptions().size() > 0) {
                for (Option exclusion : option.getExclusionOptions()) {
                    if (connectedOptions.contains(exclusion)) return false;
                }
            }
        }
        return true;
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
        return contractDto;
    }

    @Transactional
    @Override
    public ContractDto findByPhoneDto(String phone, String path) {
        Contract contract = contractDAO.findByPhone(phone);
        if (contract == null) {
            throw new NotExistClientException("The client doesn't exist", path);
        }
        ContractDto contractDto = (ContractDto) convertToDto(contract, new ContractDto());
        contractDto.setClientDto((ClientDto) convertToDto(contract.getClient(), new ClientDto()));
        contractDto.setTariffDto((TariffDto) convertToDto(contract.getTariff(), new TariffDto()));
        return contractDto;
    }

    @Transactional
    @Override
    public ContractDto block(String id) {
        Contract contract = contractDAO.findById(Integer.parseInt(id));
        contract.setBlockByOperator(true);
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
        Contract contract = contractDAO.findById(Integer.parseInt(id));
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
    public String update(ContractDto contractDto, String[] connectedOptions) {
        Contract oldContract = contractDAO.findById(contractDto.getId());
        Contract newContract = (Contract) convertToEntity(new Contract(), contractDto);
        newContract.setConnectedOptions(new HashSet<>());
        Client client = (Client) convertToEntity(new Client(), contractDto.getClientDto());
        Tariff tariff = (Tariff) convertToEntity(new Tariff(), contractDto.getTariffDto());
        Authorization authorization = (Authorization) convertToEntity(new Authorization(), contractDto.getAuth());
        newContract.setClient(client);
        newContract.setTariff(tariff);
        newContract.setAuth(authorization);
        if (connectedOptions != null && connectedOptions.length > 0) {
            for (String id : connectedOptions) {
                Option option = optionDao.findById(Integer.parseInt(id));
                newContract.getConnectedOptions().add(option);
            }
        }
        for (Option option : oldContract.getConnectedOptions()) {
            if ((oldContract.getTariff().getId() != tariff.getId()) && (tariff.getOptions().contains(option))) {
                newContract.getConnectedOptions().add(option);
            }
        }
        if (!checkOptions(newContract.getConnectedOptions())) return "Change failed. Check connection options";
        contractDAO.update(newContract);

        return "Contract changes were successful!";
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

    @Override
    public Boolean checkContract(ContractDto contractDto, OrderDto orderDto) {
        if (contractDto.getBlockByClient() == true || contractDto.getBlockByOperator() == true) {
            orderDto.setTariffDto(null);
            orderDto.setOptionsFromCurTariff(new HashSet<>());
            orderDto.setOptionsFromNewTariff(new HashSet<>());
            orderDto.setDisableOptionsFromCurTariff(new HashSet<>());
            return false;
        }
        return true;
    }

    @Override
    public void checkOrder(OrderDto orderDto, String path) {
        if ((path.equals("changecurrentoptions") || path.equals("disableoption")) && orderDto.getTariffDto() != null) {
            orderDto.setTariffDto(null);
            orderDto.setOptionsFromNewTariff(new HashSet<>());
        } else if (path.equals("addtarifftoorder") && orderDto.getOptionsFromCurTariff().size() > 0) {
            orderDto.setOptionsFromCurTariff(new HashSet<>());
            orderDto.setDisableOptionsFromCurTariff(new HashSet<>());
        }

    }

    @Override
    public void deleteTariffFromOrder(OrderDto orderDto) {
        orderDto.setTariffDto(null);
        orderDto.setOptionsFromNewTariff(new HashSet<>());
    }

    @Override
    public void deleteOptionFromOrder(Set<OptionDto> optionsFromNewTariff, Integer id) {
        optionsFromNewTariff.removeIf(option -> option.getId() == id);

    }

    @Transactional
    @Override
    public String confirmContractUserChanges(ContractDto contractDto, OrderDto orderDto) {
        Contract contract = contractDAO.findById(contractDto.getId());
        log.info(contract);
        if (!checkContract(contractDto, orderDto)) return "Your contract is blocked.";
        else if (checkOnNewTariff(contractDto, orderDto)) return "Changes were successful";
        else if (checkOnChangesCurrentOptions(contractDto, orderDto)) return "Changes were successful";
        return "Something was wrong. Check your choice and try again.";
    }

    private boolean checkOnChangesCurrentOptions(ContractDto contractDto, OrderDto orderDto) {
        if (orderDto.getOptionsFromCurTariff().size() > 0 || orderDto.getDisableOptionsFromCurTariff().size() > 0) {
            if (orderDto.getOptionsFromCurTariff().size() > 0) {
                for (OptionDto optionDto : orderDto.getOptionsFromCurTariff()) {
                    for (OptionDto reqOptionDto : optionDto.getRequiredOptions()) {
                        if ((!orderDto.getOptionsFromCurTariff().contains(reqOptionDto) &&
                                !contractDto.getConnectedOptions().contains(reqOptionDto)) ||
                                orderDto.getDisableOptionsFromCurTariff().contains(reqOptionDto)) {
                            return false;
                        }
                    }
                    for (OptionDto excOptionDto : optionDto.getExclusionOptions()) {
                        if (orderDto.getOptionsFromCurTariff().contains(excOptionDto) ||
                                contractDto.getConnectedOptions().contains(excOptionDto))
                            return false;
                    }
                }
                for (OptionDto connectedOption : contractDto.getConnectedOptions()) {
                    for (OptionDto exclOptionDto : connectedOption.getExclusionOptions()) {
                        if (orderDto.getOptionsFromCurTariff().contains(exclOptionDto)) return false;
                    }
                }
            } else if (orderDto.getDisableOptionsFromCurTariff().size() > 0) {
                for (OptionDto optionDto : orderDto.getDisableOptionsFromCurTariff()) {
                    for (OptionDto connectedOption : contractDto.getConnectedOptions()) {
                        if (connectedOption.getRequiredOptions().contains(optionDto) &&
                                !orderDto.getDisableOptionsFromCurTariff().contains(connectedOption)) return false;
                    }
                }
            }
            contractDto.getConnectedOptions().removeAll(orderDto.getDisableOptionsFromCurTariff());
            contractDto.getConnectedOptions().addAll(orderDto.getOptionsFromCurTariff());
            Contract contract = (Contract) convertToEntity(new Contract(), contractDto);
            contract.setTariff((Tariff) convertToEntity(new Tariff(), contractDto.getTariffDto()));
            contract.setClient((Client) convertToEntity(new Client(), contractDto.getClientDto()));
            log.info("contact after convert " + contract.getConnectedOptions());
            orderDto.setTariffDto(null);
            orderDto.setOptionsFromCurTariff(new HashSet<>());
            orderDto.setOptionsFromNewTariff(new HashSet<>());
            orderDto.setDisableOptionsFromCurTariff(new HashSet<>());
            contractDAO.update(contract);
            return true;

        } else return false;
    }

    private boolean checkOnNewTariff(ContractDto contractDto, OrderDto orderDto) {
        if (orderDto.getTariffDto() != null) {
            if (orderDto.getOptionsFromNewTariff().size() > 0) {
                for (OptionDto optionDto : orderDto.getOptionsFromNewTariff()) {
                    for (OptionDto reqOptionDto : optionDto.getRequiredOptions()) {
                        if ((!orderDto.getOptionsFromNewTariff().contains(reqOptionDto))) {
                            return false;
                        }
                    }
                    for (OptionDto excOptionDto : optionDto.getExclusionOptions()) {
                        if (orderDto.getOptionsFromNewTariff().contains(excOptionDto))
                            return false;
                    }
                }
                for (OptionDto connectedOption : contractDto.getConnectedOptions()) {
                    for (OptionDto exclOptionDto : connectedOption.getExclusionOptions()) {
                        if (orderDto.getOptionsFromCurTariff().contains(exclOptionDto)) return false;
                    }
                }
            }
            for (OptionDto optionDto : contractDto.getConnectedOptions()) {
                if (orderDto.getTariffDto().getOptions().contains(optionDto) &&
                        !orderDto.getOptionsFromNewTariff().contains(optionDto)) {
                    orderDto.getOptionsFromNewTariff().add(optionDto);
                }
            }
            contractDto.setTariffDto(orderDto.getTariffDto());
            contractDto.setConnectedOptions(orderDto.getOptionsFromNewTariff());
            Contract contract = (Contract) convertToEntity(new Contract(), contractDto);
            contract.setTariff((Tariff) convertToEntity(new Tariff(), contractDto.getTariffDto()));
            contract.setClient((Client) convertToEntity(new Client(), contractDto.getClientDto()));
            log.info("checkOnNewTariff contract = " + contract);
            orderDto.setTariffDto(null);
            orderDto.setOptionsFromCurTariff(new HashSet<>());
            orderDto.setOptionsFromNewTariff(new HashSet<>());
            orderDto.setDisableOptionsFromCurTariff(new HashSet<>());
            contractDAO.update(contract);
            return true;
        } else return false;
    }


    @Override
    @Transactional
    public void blockByUser(ContractDto contractDto) {
        if (contractDto.getBlockByOperator() != true) {
            Contract contract = contractDAO.findById(contractDto.getId());
            contract.setBlockByClient(true);
            contractDAO.update(contract);
            contractDto.setBlockByClient(true);
        }
    }

    @Override
    @Transactional
    public void unblockByUser(ContractDto contractDto) {
        if (contractDto.getBlockByOperator() != true) {
            Contract contract = contractDAO.findById(contractDto.getId());
            contract.setBlockByClient(false);
            contractDAO.update(contract);
            contractDto.setBlockByClient(false);
        }
    }
}
