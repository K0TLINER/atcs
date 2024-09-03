package com.example.atcs.data.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.util.Objects;

public class GameQueueValueSerializer implements RedisSerializer<GameQueueValue> {
    public static final ObjectMapper MAPPER = new ObjectMapper();
    private final Charset UTF_8 = Charset.forName("UTF-8");
    @Override
    public byte[] serialize(GameQueueValue value) throws SerializationException {
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            String json = MAPPER.writeValueAsString(value);
            return json.getBytes(UTF_8);
        } catch (JsonProcessingException e) {
            throw new SerializationException("json serialize error", e);
        }
    }

    @Override
    public GameQueueValue deserialize(byte[] bytes) throws SerializationException {
        if (Objects.isNull(bytes))
            throw null;
        try {
            return MAPPER.readValue(new String(bytes, UTF_8), GameQueueValue.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException("json deserialize error", e);
        }
    }
}
