package com.example.atcs.data.queue;

import com.example.atcs.data.info.GameInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MessageQueueValue {
    private GameInfo gameInfo;
    private String memberId;

    public MessageQueueValue(GameInfo gameInfo, String memberId) {
        this.gameInfo = gameInfo;
        this.memberId = memberId;
    }

    @JsonCreator
    public static MessageQueueValue of(
            @JsonProperty("gameInfo") GameInfo gameInfo,
            @JsonProperty("memberId") String memberId
    ) {
        return new MessageQueueValue(gameInfo, memberId);
    }
}
