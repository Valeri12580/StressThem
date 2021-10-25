package com.stressthem.app.domain;

import lombok.Getter;

@Getter
public enum TempUnit {

    FAHRENHEIT  ("imperial"),
    CELSIUS("metric");

    private String value;

    TempUnit(String value) {
        this.value = value;
    }

}
