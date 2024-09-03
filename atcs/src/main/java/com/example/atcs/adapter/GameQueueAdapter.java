package com.example.atcs.adapter;

import com.example.atcs.data.queue.GameQueueKey;
import com.example.atcs.data.queue.GameQueueValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
public class GameQueueAdapter {
    private final ListOperations<GameQueueKey, GameQueueValue> queueOperations;
    private final RedisTemplate<GameQueueKey, String> gameQueueWaitingRedisTemplate;

    public GameQueueAdapter(
            @Qualifier("gameQueueRedisTemplate") RedisTemplate gameQueueRedisTemplate,
            @Qualifier("gameQueueWaitingTimeRedisTemplate") RedisTemplate gameQueueWaitingRedisTemplate
    ) {
        queueOperations = gameQueueRedisTemplate.opsForList();
        this.gameQueueWaitingRedisTemplate = gameQueueWaitingRedisTemplate;
    }
    public GameQueueValue pop(GameQueueKey key) {
        GameQueueValue value = queueOperations.leftPop(key);
        log.info(String.format("redis LPOP %s: %s", key, value));
        updateWaitingTime(key, value.getTimestamp());
        return value;
    }
    public Long size(GameQueueKey key) { return queueOperations.size(key); }
    private void updateWaitingTime(GameQueueKey key, String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime parsedDateTime = LocalDateTime.parse(timestamp, formatter);
        LocalDateTime now = LocalDateTime.now();
        Long millisecondsDifference = ChronoUnit.MILLIS.between(parsedDateTime, now);
        log.info(String.format("redis SET %s %d", key.getWaitingKey(), millisecondsDifference));
        gameQueueWaitingRedisTemplate.opsForValue().set(key.getWaitingKey(), millisecondsDifference.toString());
    }
}
