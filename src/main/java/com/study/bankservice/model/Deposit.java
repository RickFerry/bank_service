package com.study.bankservice.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Deposit implements Serializable {
    String name;
    BigDecimal minimalSum;
    BigDecimal currentSum;
    OffsetDateTime openDate;
    OffsetDateTime closeDate;
    Double percentage;
    Boolean isCapitalized;
    String currency;
    Integer termInMonths;
}
