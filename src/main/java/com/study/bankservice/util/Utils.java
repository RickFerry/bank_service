package com.study.bankservice.util;

import com.study.bankservice.model.Client;
import com.study.bankservice.model.Deposit;
import com.study.bankservice.model.Passport;
import com.study.bankservice.model.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.study.bankservice.util.Constants.*;
import static java.time.LocalDate.parse;
import static java.time.OffsetDateTime.now;

@Component
public class Utils {

    private Utils() {
    }

    public static Map<String, Object> setClient(Client client) {
        return Map.of("client", client);
    }

    public static Client getValidClient() {
        return
                Client.builder()
                .id("1")
                .name("John")
                .surname("Doe")
                .address(MAIN_ST)
                .phoneNumber("555-1234")
                .birthDate(parse(BIRTH_AND_VALID_FROM))
                .wallet(Wallet.builder()
                        .moneyCount(BigDecimal.valueOf(1000))
                        .build())
                .passport(
                        Passport.builder()
                                .identicalNumber("AB1234567")
                                .name(JOHN_DOE)
                                .surname("Doe")
                                .address(MAIN_ST)
                                .birthDate(parse(BIRTH_AND_VALID_FROM))
                                .validFrom(parse(BIRTH_AND_VALID_FROM))
                                .validTo(parse(DATE_VALID_TO))
                                .build()
                )
                .build();
//        Client.builder()
//                .id("4")
//                .name("Mike")
//                .surname(SMITH)
//                .address(MAIN_ST)
//                .phoneNumber("555-1234")
//                .birthDate(parse(BIRTH_AND_VALID_FROM))
//                .wallet(null)
//                .passport(
//                        Passport.builder()
//                                .identicalNumber("HI7654321")
//                                .name(JOHN_DOE)
//                                .surname(SMITH)
//                                .address(MAIN_ST)
//                                .birthDate(parse(BIRTH_AND_VALID_FROM))
//                                .validFrom(parse(BIRTH_AND_VALID_FROM))
//                                .validTo(parse(DATE_VALID_TO))
//                                .build()
//                )
//                .build();
    }

    public static List<Client> getBadClientList() {
        return List.of(Client.builder()
                .id("4")
                .name("Mike")
                .surname(SMITH)
                .address(MAIN_ST)
                .phoneNumber("555-1234")
                .birthDate(parse(BIRTH_AND_VALID_FROM))
                .wallet(null)
                .passport(
                        Passport.builder()
                                .identicalNumber("HI7654321")
                                .name(JOHN_DOE)
                                .surname(SMITH)
                                .address(MAIN_ST)
                                .birthDate(parse(BIRTH_AND_VALID_FROM))
                                .validFrom(parse(BIRTH_AND_VALID_FROM))
                                .validTo(parse(DATE_VALID_TO))
                                .build()
                )
                .build());
    }

    public static List<Passport> getPassports() {
        return List.of(
                Passport.builder()
                        .identicalNumber("ID654321")
                        .name("Jane Smith")
                        .surname(SMITH)
                        .address("456 Elm St")
                        .birthDate(parse("1985-05-15"))
                        .validFrom(parse("2016-06-01"))
                        .validTo(parse("2026-06-01")).build(),
                Passport.builder()
                        .identicalNumber("ID789012")
                        .name("Alice Johnson")
                        .surname("Johnson")
                        .address("789 Oak St")
                        .birthDate(parse("1978-09-30"))
                        .validFrom(parse("2017-03-15"))
                        .validTo(parse("2027-03-15")).build(),
                Passport.builder()
                        .identicalNumber("AB1234567")
                        .name(JOHN_DOE)
                        .surname("Doe")
                        .address(MAIN_ST)
                        .birthDate(parse(BIRTH_AND_VALID_FROM))
                        .validFrom(parse(BIRTH_AND_VALID_FROM))
                        .validTo(parse(DATE_VALID_TO)).build()
        );
    }

    public static List<Deposit> getBankListProvided() {
        return List.of(
                Deposit.builder()
                        .name("Standard Deposit")
                        .minimalSum(new BigDecimal("1000"))
                        .currentSum(new BigDecimal("5000"))
                        .openDate(now())
                        .closeDate(now().plusMonths(12))
                        .percentage(3.5)
                        .isCapitalized(true)
                        .currency("USD")
                        .termInMonths(12)
                        .build(),
                Deposit.builder()
                        .name("Premium Deposit")
                        .minimalSum(new BigDecimal("5000"))
                        .currentSum(new BigDecimal("20000"))
                        .openDate(now())
                        .closeDate(now().plusMonths(24))
                        .percentage(5.0)
                        .isCapitalized(false)
                        .currency("EUR")
                        .termInMonths(24)
                        .build(),
                Deposit.builder()
                        .name("Savings Deposit")
                        .minimalSum(new BigDecimal("2000"))
                        .currentSum(new BigDecimal("10000"))
                        .openDate(now())
                        .closeDate(now().plusMonths(18))
                        .percentage(4.0)
                        .isCapitalized(true)
                        .currency("GBP")
                        .termInMonths(18)
                        .build()
        );
    }

    public static boolean isClient(List<Passport> passports, Passport passport) {
        return passports.stream().anyMatch(p ->
                p.getIdenticalNumber().equals(passport.getIdenticalNumber())
                        && p.getName().equals(passport.getName())
                        && p.getSurname().equals(passport.getSurname())
                        && p.getBirthDate().equals(passport.getBirthDate()));
    }

    public static boolean isPoliceOrBlackList(Client client, List<Client> badClientList) {
        return badClientList.stream().anyMatch(c ->
                c.getName().equals(client.getName())
                        && c.getSurname().equals(client.getSurname())
                        && c.getBirthDate().equals(client.getBirthDate())
                        && c.getPassport().getIdenticalNumber().equals(client.getPassport().getIdenticalNumber()));
    }
}
