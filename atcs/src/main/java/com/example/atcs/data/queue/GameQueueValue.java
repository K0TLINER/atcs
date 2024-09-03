package com.example.atcs.data.queue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
public class GameQueueValue {
    private String memberId;
    private String timestamp;
    private GameQueueValue(String memberId) {
        if (Objects.isNull(memberId)) {
            throw new IllegalArgumentException("memberId can't be null");
        }
        this.memberId = memberId;
        this.timestamp = LocalDateTime.now().toString();
    }
    @JsonCreator
    public static GameQueueValue of(
            @JsonProperty("memberId") String memberId
    ) {
        return new GameQueueValue(memberId);
    }
}
