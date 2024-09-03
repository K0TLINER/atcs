package com.example.nxcommand.channel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Channel {
    MESSAGE_REGISTER("atcs_message_queue_register"),
    MESSAGE_SEND("atcs_message_queue_send"),
    MESSAGE_SUCCESS("atcs_message_success"),
    REQUEST_DECREASE("game_server_request_decrease");
    private String topic;
    Channel(String topic) {
        this.topic = topic;
    }
//    @JsonValue
    public String getTopic() {
        return topic;
    }
    @JsonCreator
    public static Channel forValue(String value) {
        for (Channel channel: values()) {
            if (channel.getTopic().equals(value)) {
                return channel;
            }
        }
        throw new IllegalArgumentException("Unknown topic : " + value);
    }
}
