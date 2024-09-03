package com.example.nxcommand.adapter;

import com.example.nxcommand.data.queue.MessageQueueKey;
import com.example.nxcommand.data.queue.MessageQueueValue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageQueueAdapter {
    private final RedisTemplate<MessageQueueKey, MessageQueueValue> messageQueueRedisTemplate;
    private final ListOperations<MessageQueueKey, MessageQueueValue> listOperations;

    public MessageQueueAdapter(@Qualifier("messageQueueRedisTemplate") RedisTemplate messageQueueRedisTemplate) {
        this.messageQueueRedisTemplate = messageQueueRedisTemplate;
        this.listOperations = messageQueueRedisTemplate.opsForList();
    }
    public MessageQueueValue leftPop() {
        return listOperations.leftPop(MessageQueueKey.of());
    }
}
