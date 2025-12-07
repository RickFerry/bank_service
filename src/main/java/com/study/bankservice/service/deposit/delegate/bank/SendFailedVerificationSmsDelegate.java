package com.study.bankservice.service.deposit.delegate.bank;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.study.bankservice.util.Constants.START_FAILED_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Component("sendFailedVerificationSmsDelegate")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SendFailedVerificationSmsDelegate implements JavaDelegate {

    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("The sendFailedVerificationSmsDelegate has started.");

        String businessKey = delegateExecution.getBusinessKey();

        runtimeService.createMessageCorrelation(START_FAILED_MESSAGE)
                .processInstanceBusinessKey(businessKey)
                .correlateWithResult();
    }
}
