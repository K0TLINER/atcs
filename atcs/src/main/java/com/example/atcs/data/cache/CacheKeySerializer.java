package com.example.atcs.data.cache;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.util.Objects;

public class CacheKeySerializer implements RedisSerializer<CacheKey> {
    private final Charset UTF_8 = Charset.forName("UTF-8");
    @Override
    public byte[] serialize(CacheKey value) throws SerializationException {
//        return new byte[0];
        if (Objects.isNull(value)) {
            throw new SerializationException("cacheKey is null");
        }
        return value.toString().getBytes(UTF_8);
    }

    @Override
    public CacheKey deserialize(byte[] bytes) throws SerializationException {
        if (Objects.isNull(bytes))
            throw new SerializationException("bytes is null");
        return CacheKey.fromString(new String(bytes, UTF_8));
    }
}
