package com.onlinetrading.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppStatus{
    WAITING("Ожидание"),
    CONFIRMED("Одобрено"),
    REJECT("Отказано"),
    ;

    private final String name;
}
