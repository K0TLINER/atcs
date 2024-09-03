package com.example.nxcommand.channel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;

@Slf4j
public abstract class AbstractChannelSubscriber implements MessageListener {
    protected final static ObjectMapper MAPPER = new ObjectMapper();
    public abstract String getTopic();
    public abstract void onMessage(Channel channel, EventMessage eventMessage);
    @Override
    public final void onMessage(Message message, byte[] pattern) {
        try {
            String messageJson = new String(message.getBody(), StandardCharsets.UTF_8);
            Channel channel = Channel.forValue(new String(message.getChannel(), StandardCharsets.UTF_8));
            EventMessage eventMessage = MAPPER.readValue(messageJson, EventMessage.class);
            log.info(String.format("redis SUBSCRIBE %s: %s", channel.getTopic(), eventMessage));
            onMessage(channel, eventMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
