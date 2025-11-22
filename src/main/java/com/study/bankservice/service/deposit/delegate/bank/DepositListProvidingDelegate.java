package com.study.bankservice.service.deposit.delegate.bank;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.study.bankservice.util.Utils.getBankListProvided;

@Slf4j
@Component("depositListProvidingDelegate")
public class DepositListProvidingDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("The deposit list providing has started.");

        // Todo implement deposit list providing logic
        log.info("The deposit list has been provided {} deposits", getBankListProvided().size());

        delegateExecution.setVariable("bankDeposits", getBankListProvided());
    }
}
