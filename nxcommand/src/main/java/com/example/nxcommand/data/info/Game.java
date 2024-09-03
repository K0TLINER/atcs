package com.example.nxcommand.data.info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Game {
    MAPLE("maple"),
    BARAM("baram"),
    KART("kart");
    private String title;
    Game(String title) {
        this.title = title;
    }
    @JsonValue
    public String getTitle() {
        return title;
    }
    @JsonCreator
    public static Game forValue(String value) {
        for (Game game: values()) {
            if (game.getTitle().equals(value)) {
                return game;
            }
        }
        throw new IllegalArgumentException("Unknown game title : " + value);
    }
}
