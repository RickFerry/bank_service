package com.study.bankservice.service.deposit.delegate.bank;

import com.study.bankservice.model.Client;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.study.bankservice.util.Constants.MESSAGE_START_SMS_VERIFICATION;

@Slf4j
@RequiredArgsConstructor
@Component("startVerificationSMSDelegate")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StartVerificationSMSDelegate implements JavaDelegate {
    
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("The startVerificationSMSDelegate has started.");

        String businessKey = delegateExecution.getBusinessKey();
        Client client = (Client) delegateExecution.getVariable("client");

        runtimeService.createMessageCorrelation(MESSAGE_START_SMS_VERIFICATION)
                .processInstanceBusinessKey(businessKey)
                .setVariable("client", client)
                .correlateWithResult();
    }
}
