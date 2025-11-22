package com.study.bankservice.service;

import com.study.bankservice.model.Client;

public interface ValidationService {
    boolean isClientWantedByPolice(Client client);
    boolean isClientInBlacklist(Client client);
    boolean isValidPssport(Client client);
}
