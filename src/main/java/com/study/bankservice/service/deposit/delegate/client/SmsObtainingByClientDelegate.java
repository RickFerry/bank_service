package com.study.bankservice.service.deposit.delegate.client;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component("smsObtainingByClientDelegate")
public class SmsObtainingByClientDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("The smsObtainingByClientDelegate has started.");

        Map<String, Object> variables = delegateExecution.getVariables();

        if (!variables.containsKey("sendMobileCode")) {
            throw new BpmnError("VERIFICATION_SMS_NOT_OBTAINED", "The verification SMS is not obtained by client.");
        }
    }
}
