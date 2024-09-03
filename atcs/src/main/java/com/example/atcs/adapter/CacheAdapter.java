package com.example.atcs.adapter;

import com.example.atcs.data.cache.CacheKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CacheAdapter {
    private final RedisTemplate<CacheKey, Long> cacheRedisTemplate;
    private final ValueOperations<CacheKey, Long> valueOperations;
    private final RedisScript<Long> increaseRequestCountRedisScript;
    private final RedisScript<Long> decreaseRequestCountRedisScript;
    public CacheAdapter(
            @Qualifier("cacheRedisTemplate") RedisTemplate<CacheKey, Long> cacheRedisTemplate,
            @Qualifier("increaseRequestCountRedisScript") RedisScript<Long> increaseRequestCountRedisScript,
            @Qualifier("decreaseRequestCountRedisScript") RedisScript<Long> decreaseRequestCountRedisScript
    ) {
        this.cacheRedisTemplate = cacheRedisTemplate;
        this.increaseRequestCountRedisScript = increaseRequestCountRedisScript;
        this.decreaseRequestCountRedisScript = decreaseRequestCountRedisScript;
        valueOperations = cacheRedisTemplate.opsForValue();
    }
    public Long increase(CacheKey maxKey, CacheKey currentKey) {
        Long count = cacheRedisTemplate.execute(increaseRequestCountRedisScript, List.of(maxKey, currentKey));
        log.info(String.format("redis GET %s: %d", currentKey, count));
        return count;
    }
    public Long decrease(CacheKey currentKey) {
        Long count = cacheRedisTemplate.execute(decreaseRequestCountRedisScript, List.of(currentKey));
        log.info(String.format("redis GET %s: %d", currentKey, count));
        return count;
    }
}
