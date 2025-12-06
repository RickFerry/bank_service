package com.study.bankservice.service.deposit.delegate.bank;

import com.study.bankservice.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.study.bankservice.util.Constants.SEND_MOBILE_CODE_COUNT;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Slf4j
@Component("prepareSmsDelegate")
public class PrepareSmsDelegate implements JavaDelegate {

    private static final Random RANDOM = new Random();

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("The prepareSmsDelegate has started.");

        Client client = (Client) delegateExecution.getVariable("client");
        log.info("Preparing SMS for client: {} with phone number: {}", client.getName(), client.getPhoneNumber());

        int code = RANDOM.nextInt(1_000_000);
        log.info("Generated SMS code: {} for client: {}", format("%06d", code), client.getName());

        delegateExecution.setVariable("sendMobileCode", code);
        Integer sendMobileCodeCount = (Integer) delegateExecution.getVariable(SEND_MOBILE_CODE_COUNT);

        ofNullable(sendMobileCodeCount)
                .ifPresentOrElse(
                        count -> delegateExecution.setVariable(SEND_MOBILE_CODE_COUNT, count + 1),
                        () -> delegateExecution.setVariable(SEND_MOBILE_CODE_COUNT, 1)
                );
    }
}
