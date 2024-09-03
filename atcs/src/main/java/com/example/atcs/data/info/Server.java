package com.example.atcs.data.info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Server {
    QA01("qa-01"),
    QA02("qa-02"),
    STAGE("stage");
    private String title;
    Server(String title) {
        this.title = title;
    }
    @JsonValue
    public String getTitle() {
        return title;
    }
    @JsonCreator
    public static Server forValue(String value) {
        for (Server server: values()) {
            if (server.getTitle().equals(value)) {
                return server;
            }
        }
        throw new IllegalArgumentException("Unknown game title : " + value);
    }
}
