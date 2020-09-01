package com.task.service;

import com.task.dto.TariffDto;

import java.util.HashSet;

public interface TariffSender {
    void sendTariffs(final HashSet<TariffDto> tariffDto);
}
