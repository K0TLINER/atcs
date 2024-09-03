package com.example.nxcommand.adapter;

import com.example.nxcommand.data.info.GameInfo;
import com.example.nxcommand.data.queue.GameQueueKey;
import com.example.nxcommand.data.queue.GameQueueValue;
import com.example.nxcommand.exception.ManyRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@Slf4j
public class GameQueueAdapter {
    private final static int TIMEOUT_TIME = 10000;
    private final ListOperations<GameQueueKey, GameQueueValue> queueOperations;
    private final RedisTemplate<GameQueueKey, String> gameQueueWaitingRedisTemplate;

    public GameQueueAdapter(
            @Qualifier("gameQueueRedisTemplate") RedisTemplate gameQueueRedisTemplate,
            @Qualifier("gameQueueWaitingTimeRedisTemplate") RedisTemplate gameQueueWaitingRedisTemplate
    ) {
        queueOperations = gameQueueRedisTemplate.opsForList();
        this.gameQueueWaitingRedisTemplate = gameQueueWaitingRedisTemplate;
    }
    public void push(GameInfo gameInfo, String memberId) {
        GameQueueKey key = GameQueueKey.from(gameInfo);
        GameQueueValue value = GameQueueValue.of(memberId);
        if (isWaiting(key)) {
            throw new ManyRequestException(key.getGame(), value.getMemberId());
        }
        queueOperations.rightPush(key, value);
        String.format("redis RPUSH %s %s", key, value.getMemberId());
    }
    public GameQueueValue pop(GameQueueKey key) {
        return queueOperations.leftPop(key);
    }
    private boolean isWaiting(GameQueueKey key) {
        // 대기열 대기시간은 ATCS에서 메시자가 인출된 시간과 메시지에 저장된 시간을 비교해 초기화한다.
        // 만료시간을 등록해서 일정 시간이 지나면 값을 만료시켜 다시 정상적으로 메시지가 대기열에 등록될 수 있게한다.
        String waitingTime = gameQueueWaitingRedisTemplate.opsForValue().get(key.getWaitingKey());
        log.info(String.format("redis GET %s: %s", key.getWaitingKey(), waitingTime));
        if (Objects.nonNull(waitingTime) && Integer.parseInt(waitingTime) > TIMEOUT_TIME) {
            return true;
        }
        return false;
    }
}
