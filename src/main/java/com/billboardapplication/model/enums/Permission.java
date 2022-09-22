package com.billboardapplication.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    USER("user:write"),
    MODERATE("user:moderate");

    private final String permission;

}
