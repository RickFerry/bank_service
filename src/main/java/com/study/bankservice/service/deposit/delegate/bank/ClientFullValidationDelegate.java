package com.study.bankservice.service.deposit.delegate.bank;

import com.study.bankservice.model.Client;
import com.study.bankservice.service.ValidationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component("clientFullValidationDelegate")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ClientFullValidationDelegate implements JavaDelegate {

    ValidationService validationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("The client full validation started.");

        Client client = (Client) delegateExecution.getVariable("client");

        boolean isCriminal = validationService.isClientWantedByPolice(client);
        boolean isInBlacklist = validationService.isClientInBlacklist(client);
        boolean isValidPassport = validationService.isValidPssport(client);

        boolean isValidUser = !isCriminal && !isInBlacklist && isValidPassport;

        delegateExecution.setVariable("isCriminal", isCriminal);
        delegateExecution.setVariable("isValidUser", isValidUser);
    }
}
