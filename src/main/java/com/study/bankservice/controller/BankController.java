package com.study.bankservice.controller;

import com.study.bankservice.model.Client;
import com.study.bankservice.model.Passport;
import com.study.bankservice.model.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngines;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static com.study.bankservice.util.Constants.BIRTH_AND_VALID_FROM;
import static com.study.bankservice.util.Constants.MAIN_DEPOSIT_CREDIT_PROCESS;

@Slf4j
@RestController
@RequestMapping("/bank")
public class BankController {

    @PostMapping("/start/{businessProcessId}")
    public ResponseEntity<String> startBankProcess(@PathVariable("businessProcessId") String businessProcessId) {
        log.info("Bank process started for BusinessProcessId: {}", businessProcessId);

        if (StringUtils.isBlank(businessProcessId)) {
            log.error("Invalid BusinessProcessId: {}", businessProcessId);
            return ResponseEntity.badRequest().body("Invalid BusinessProcessId");
        }

        ProcessEngines
                .getDefaultProcessEngine()
                .getRuntimeService()
                .createProcessInstanceByKey(MAIN_DEPOSIT_CREDIT_PROCESS)
                .businessKey(businessProcessId)
                .setVariables(setClient(getClient()))
                .executeWithVariablesInReturn();

        log.info("Bank process initiated successfully for BusinessProcessId: {}", businessProcessId);
        return ResponseEntity.ok("Bank process started successfully for BusinessProcessId: " + businessProcessId);
    }

    private Map<String, Object> setClient(Client client) {
        return Map.of("client", client);
    }

    private Client getClient() {
        return Client.builder()
                .id("1")
                .name("John")
                .surname("Doe")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .birthDate(LocalDate.parse(BIRTH_AND_VALID_FROM))
                .wallet(Wallet.builder()
                        .moneyCount(BigDecimal.valueOf(1000))
                        .build())
                .passport(
                        Passport.builder()
                        .identicalNumber("AB1234567")
                        .name("John Doe")
                        .surname("Doe")
                        .address("123 Main St")
                        .birthDate(LocalDate.parse(BIRTH_AND_VALID_FROM))
                        .validFrom(LocalDate.parse(BIRTH_AND_VALID_FROM))
                        .validTo(LocalDate.parse("1990-01-01"))
                        .build()
                )
                .build();
    }
}
