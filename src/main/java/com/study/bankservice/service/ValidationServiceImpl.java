package com.study.bankservice.service;

import com.study.bankservice.model.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.study.bankservice.util.Utils.getBadClientList;
import static com.study.bankservice.util.Utils.isPoliceOrBlackList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean isClientWantedByPolice(Client client) {
        List<Client> badClientList = getBadClientList();
        return isPoliceOrBlackList(client, badClientList);
    }

    @Override
    public boolean isClientInBlacklist(Client client) {
        return this.isClientWantedByPolice(client);
    }

    @Override
    public boolean isValidPssport(Client client) {
        return client.getPassport().getValidFrom().isBefore(LocalDate.now())
                && client.getPassport().getValidTo().isAfter(LocalDate.now());
    }
}
