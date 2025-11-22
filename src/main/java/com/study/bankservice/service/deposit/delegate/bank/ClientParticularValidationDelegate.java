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
@Component("clientParticularValidationDelegate")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ClientParticularValidationDelegate implements JavaDelegate {

    ValidationService validationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("The client particular validation started.");

        Client client = (Client) delegateExecution.getVariable("client");
        boolean isCriminal = validationService.isClientWantedByPolice(client);

        delegateExecution.setVariable("isCriminal", isCriminal);
        delegateExecution.setVariable("isValidUser", !isCriminal);
    }
}
