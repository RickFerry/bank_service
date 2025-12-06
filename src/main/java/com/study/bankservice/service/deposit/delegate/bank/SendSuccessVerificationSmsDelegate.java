package com.study.bankservice.service.deposit.delegate.bank;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.study.bankservice.util.Constants.MESSAGE_SUCCESS_SMS_VERIFICATION;

@Slf4j
@RequiredArgsConstructor
@Component("sendSuccessVerificationSmsDelegate")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SendSuccessVerificationSmsDelegate implements JavaDelegate {

    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("The SendSuccessVerificationSmsDelegate has started.");

        String businessKey = delegateExecution.getBusinessKey();

        runtimeService.createMessageCorrelation(MESSAGE_SUCCESS_SMS_VERIFICATION)
                .processInstanceBusinessKey(businessKey)
                .correlateWithResult();
    }
}
