package com.onlinetrading.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Coupons {
    A1010(1),
    G5070(57),
    ;

    private final int discount;
}
