package com.study.bankservice.service.deposit.delegate.bank;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static com.study.bankservice.util.Constants.LIMIT_OF_VERIFICATION_SMS_ATTEMPTS_EXCEEDED;
import static java.lang.String.format;
import static org.apache.commons.lang3.ObjectUtils.anyNull;

@Slf4j
@Component("smsValidationDelegate")
public class SmsValidationDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("The SmsValidationDelegate has started.");

        Integer mobileCode = (Integer) delegateExecution.getVariable("obtainedMobileCode");
        Integer sendMobileCode = (Integer) delegateExecution.getVariable("sendMobileCode");

        if (anyNull(mobileCode, sendMobileCode)) {
            throw new IllegalArgumentException(
                    format("One of the arguments is null: obtainedMobileCode=%s, sendMobileCode=%s", mobileCode, sendMobileCode));
        }

        if (Objects.equals(mobileCode, sendMobileCode)) {
            delegateExecution.setVariable("isSmsCodeValid", true);
        } else {
            Integer codeCount = (Integer) Optional.ofNullable(delegateExecution.getVariable("sendMobileCodeCount")).orElse(1);

            if (codeCount == 3) {
                throw new BpmnError(LIMIT_OF_VERIFICATION_SMS_ATTEMPTS_EXCEEDED,
                        "The limit of verification SMS attempts has been exceeded.");
            }
            delegateExecution.setVariable("isSmsCodeValid", false);

            log.info("The verification sms code does not match, the sent one!");
        }
    }
}
