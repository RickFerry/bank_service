package com.study.bankservice.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {
    String id;
    String name;
    String surname;
    String address;
    String phoneNumber;
    LocalDate birthDate;
    Wallet wallet;
    Passport passport;
}
