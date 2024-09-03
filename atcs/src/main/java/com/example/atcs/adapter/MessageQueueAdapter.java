package com.example.atcs.adapter;

import com.example.atcs.data.queue.MessageQueueKey;
import com.example.atcs.data.queue.MessageQueueValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageQueueAdapter {
    private final ListOperations<MessageQueueKey, MessageQueueValue> listOperations;

    public MessageQueueAdapter(@Qualifier("messageQueueRedisTemplate") RedisTemplate messageQueueRedisTemplate) {
        this.listOperations = messageQueueRedisTemplate.opsForList();
    }
    public void push(MessageQueueValue value) {
        MessageQueueKey key = MessageQueueKey.of();
        log.info(String.format("redis SET %s %s", key, value));
        listOperations.rightPush(key, value);
    }
}
