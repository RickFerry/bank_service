package com.study.bankservice.service.deposit.delegate.bank;

import com.study.bankservice.model.Client;
import com.study.bankservice.model.Passport;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.study.bankservice.util.Utils.getPassports;
import static com.study.bankservice.util.Utils.isClient;

@Slf4j
@Component("clientExistingCheckingDelegate")
public class ClientExistingCheckingDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("The clientExistingCheckingDelegate has started.");

        var client = (Client) delegateExecution.getVariable("client");
        var passport = client.getPassport();

        List<Passport> passports = getPassports();

        // Todo Call to DB for checking existing user

        boolean isExistingUser = isClient(passports, passport);
        if (isExistingUser) {
            log.info("The user with name: {} and passport ID: {} is existing.", client.getName(), client.getPassport());
        } else {
            log.info("The user with name: {} and passport ID: {} is not existing.", client.getName(), client.getPassport());
        }

        delegateExecution.setVariable("isExistingUser", isExistingUser);
    }
}
