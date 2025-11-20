package com.study.bankservice.service.way.delegate;

import com.study.bankservice.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component("taxiPaymentDelegate")
public class TaxiPaymentDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.info("The taxi payment has started.");

        Client client = (Client) delegateExecution.getVariable("client");
        String taxiCost = (String) delegateExecution.getVariable("taxiCost");

        BigDecimal moneyOnWallet = client.getWallet().getMoneyCount().subtract(new BigDecimal(taxiCost));
        log.info("Client just has peed for the taxi about {} ", taxiCost);

        client.getWallet().setMoneyCount(moneyOnWallet);
    }
}
