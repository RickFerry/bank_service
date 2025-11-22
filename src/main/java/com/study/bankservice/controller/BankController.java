package com.study.bankservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngines;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.study.bankservice.util.Constants.MAIN_DEPOSIT_CREDIT_PROCESS;
import static com.study.bankservice.util.Utils.getValidClient;
import static com.study.bankservice.util.Utils.setClient;

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
                .setVariables(setClient(getValidClient()))
                .executeWithVariablesInReturn();

        log.info("Bank process initiated successfully for BusinessProcessId: {}", businessProcessId);
        return ResponseEntity.ok("Bank process started successfully for BusinessProcessId: " + businessProcessId);
    }
}
