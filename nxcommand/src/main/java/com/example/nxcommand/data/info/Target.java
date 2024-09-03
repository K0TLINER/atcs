package com.example.nxcommand.data.info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Target {
    COUPON("coupon"),
    PLAY("play");

    private String group;
    Target(String group) {
        this.group = group;
    }
    @JsonValue
    public String getGroup() {
        return group;
    }
    @JsonCreator
    public static Target forValue(String value) {
        for (Target target: values()) {
            if (target.getGroup().equals(value)) {
                return target;
            }
        }
        throw new IllegalArgumentException("Unknown topic : " + value);
    }
}
