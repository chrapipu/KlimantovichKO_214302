package com.onlinetrading.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    ACTIVE("Активно"),
    ARCHIVE("Архивировано"),
    ;

    private final String name;
}
