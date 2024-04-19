package com.onlinetrading.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserAdd {
    CONFIRMED("Согласен"),
    REJECT("Отказываюсь"),
    ;

    private final String name;
}
