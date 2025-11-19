package com.study.bankservice.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wallet {
    BigDecimal moneyCount;
}
