package com.study.bankservice.service.deposit.delegate.bank;

import com.study.bankservice.model.Client;
import com.study.bankservice.model.Passport;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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

    private static boolean isClient(List<Passport> passports, Passport passport) {
        return passports.stream().anyMatch(p ->
                p.getIdenticalNumber().equals(passport.getIdenticalNumber())
                && p.getName().equals(passport.getName())
                && p.getSurname().equals(passport.getSurname())
                && p.getBirthDate().equals(passport.getBirthDate()));
    }

    private List<Passport> getPassports() {
        return List.of(
                Passport.builder()
                        .identicalNumber("ID123456")
                        .name("John Doe")
                        .surname("Doe")
                        .address("123 Main St")
                        .birthDate(LocalDate.parse("1990-01-01"))
                        .validFrom(LocalDate.parse("2015-01-01"))
                        .validTo(LocalDate.parse("2025-01-01")).build(),
                Passport.builder()
                        .identicalNumber("ID654321")
                        .name("Jane Smith")
                        .surname("Smith")
                        .address("456 Elm St")
                        .birthDate(LocalDate.parse("1985-05-15"))
                        .validFrom(LocalDate.parse("2016-06-01"))
                        .validTo(LocalDate.parse("2026-06-01")).build(),
                Passport.builder()
                        .identicalNumber("ID789012")
                        .name("Alice Johnson")
                        .surname("Johnson")
                        .address("789 Oak St")
                        .birthDate(LocalDate.parse("1978-09-30"))
                        .validFrom(LocalDate.parse("2017-03-15"))
                        .validTo(LocalDate.parse("2027-03-15")).build()
        );
    }
}
