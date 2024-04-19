package com.onlinetrading.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("Администратор"),
    MANAGER("Менеджер"),
    FIZ("Физ. лицо"),
    LEGAL("Юр. лицо"),
    ;

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}
