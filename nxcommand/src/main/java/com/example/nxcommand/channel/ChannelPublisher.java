package com.example.nxcommand.channel;

import com.example.nxcommand.data.info.GameInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChannelPublisher {
    private final RedisTemplate<String, String> eventRedisTemplate;

    public ChannelPublisher(@Qualifier("eventRedisTemplate") RedisTemplate<String, String> eventRedisTemplate) {
        this.eventRedisTemplate = eventRedisTemplate;
    }
    public void publish(Channel channel, GameInfo gameInfo) {
        EventMessage message = EventMessage.of(gameInfo);
        log.info(String.format("redis PUBLISH %s %s", channel.getTopic(), message));
        eventRedisTemplate.convertAndSend(channel.getTopic(), message);
    }

}
