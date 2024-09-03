package com.example.nxcommand.data.queue;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.util.Objects;

public class GameQueueKeySerializer implements RedisSerializer<GameQueueKey> {
    private final Charset UTF_8 = Charset.forName("UTF-8");
    @Override
    public byte[] serialize(GameQueueKey value) throws SerializationException {
        if (Objects.isNull(value)) {
            throw new SerializationException("gameQueueKey is null");
        }
        return value.toString().getBytes(UTF_8);
    }

    @Override
    public GameQueueKey deserialize(byte[] bytes) throws SerializationException {
        if (Objects.isNull(bytes))
            throw new SerializationException("bytes is null");
        return GameQueueKey.fromString(new String(bytes, UTF_8));
    }
}
