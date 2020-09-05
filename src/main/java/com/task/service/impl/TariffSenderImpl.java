package com.task.service.impl;

import com.task.dto.TariffDto;
import com.task.service.TariffSender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@Getter
@Setter
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TariffSenderImpl implements TariffSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(TariffSenderImpl.class);
    private JmsTemplate jmsTemplate;

    public void sendTariffs(final HashSet<TariffDto> tariffDto) {
        LOGGER.info("[{}] [{}] sendTariffs: {}", LocalDateTime.now(), LOGGER.getName(),  tariffDto);
        try {
            jmsTemplate.convertAndSend(tariffDto);
        } catch (JmsException e) {
            LOGGER.error("ERROR [{}] [{}] convertAndSend: ", LocalDateTime.now(), LOGGER.getName(),  e);
        }

    }

}
