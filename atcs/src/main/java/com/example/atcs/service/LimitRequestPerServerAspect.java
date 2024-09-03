package com.example.atcs.service;

import com.example.atcs.adapter.CacheAdapter;
import com.example.atcs.adapter.GameQueueAdapter;
import com.example.atcs.channel.EventMessage;
import com.example.atcs.data.cache.CacheKey;
import com.example.atcs.data.cache.CurrentRequestCountCacheKey;
import com.example.atcs.data.cache.MaxConcurrencyCacheKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LimitRequestPerServerAspect {
    private final CacheAdapter cacheAdapter;
    private final GameQueueAdapter gameQueueAdapter;

    public LimitRequestPerServerAspect(CacheAdapter cacheAdapter, GameQueueAdapter gameQueueAdapter) {
        this.cacheAdapter = cacheAdapter;
        this.gameQueueAdapter = gameQueueAdapter;
    }

    @Around("execution(* com.example.atcs.service.*.*(..))")
    public void interceptors(ProceedingJoinPoint joinPoint) throws Throwable {
        EventMessage eventMessage = (EventMessage) joinPoint.getArgs()[0];
        CacheKey maxKey = MaxConcurrencyCacheKey.from(eventMessage.getMessage());
        CacheKey currentKey = CurrentRequestCountCacheKey.from(eventMessage.getMessage());
        Long count = cacheAdapter.increase(maxKey, currentKey);
        if (count != 0) {
            joinPoint.proceed();
        } else {
            log.error(String.format("not publish event for %s", eventMessage.getMessage()));
        }
    }
}
