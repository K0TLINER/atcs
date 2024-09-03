package com.example.nxcommand.channel;

import com.example.nxcommand.data.info.GameInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class EventMessage {
    private String timestamp;
    private GameInfo message;

    private EventMessage(GameInfo gameInfo) {
        this.message = gameInfo;
        this.timestamp = LocalDateTime.now().toString();
    }
    private EventMessage(GameInfo gameInfo, String timestamp) {
        this.message = gameInfo;
        this.timestamp = timestamp;
    }

    public static EventMessage of(GameInfo gameInfo) {
        return new EventMessage(gameInfo);
    }
    @JsonCreator
    public static EventMessage of(
            @JsonProperty("message") GameInfo gameInfo,
            @JsonProperty("timestamp") String timestamp
    ) {
        return new EventMessage(gameInfo, timestamp);
    }
}
