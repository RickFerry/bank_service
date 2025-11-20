package com.study.bankservice.service.deposit.delegate.client;

import com.study.bankservice.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.study.bankservice.util.Constants.SUDDEN_OPERATION_INTERRUPTION_ERROR;
import static java.util.Objects.isNull;

@Slf4j
@Component("passportProvidingDelegate")
public class PassportProvidingDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("The passport providing has started.");

        var client = (Client) delegateExecution.getVariable("client");

        if (isNull(client.getPassport())) {
            log.error("Client has not provided the passport.");
            throw new BpmnError(SUDDEN_OPERATION_INTERRUPTION_ERROR);
        }

        log.info("Client has provided the passport successfully: {}", client.getPassport());
    }
}
