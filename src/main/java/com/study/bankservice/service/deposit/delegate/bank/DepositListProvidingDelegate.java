package com.study.bankservice.service.deposit.delegate.bank;

import com.study.bankservice.model.Deposit;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

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

    private List<Deposit> getBankListProvided() {
        return List.of(
                Deposit.builder()
                        .name("Standard Deposit")
                        .minimalSum(new BigDecimal("1000"))
                        .currentSum(new BigDecimal("5000"))
                        .openDate(OffsetDateTime.now())
                        .closeDate(OffsetDateTime.now().plusMonths(12))
                        .percentage(3.5)
                        .isCapitalized(true)
                        .currency("USD")
                        .termInMonths(12)
                        .build(),
                Deposit.builder()
                        .name("Premium Deposit")
                        .minimalSum(new BigDecimal("5000"))
                        .currentSum(new BigDecimal("20000"))
                        .openDate(OffsetDateTime.now())
                        .closeDate(OffsetDateTime.now().plusMonths(24))
                        .percentage(5.0)
                        .isCapitalized(false)
                        .currency("EUR")
                        .termInMonths(24)
                        .build(),
                Deposit.builder()
                        .name("Savings Deposit")
                        .minimalSum(new BigDecimal("2000"))
                        .currentSum(new BigDecimal("10000"))
                        .openDate(OffsetDateTime.now())
                        .closeDate(OffsetDateTime.now().plusMonths(18))
                        .percentage(4.0)
                        .isCapitalized(true)
                        .currency("GBP")
                        .termInMonths(18)
                        .build()
        );
    }
}
