package com.example.atcs.config;

import com.example.atcs.channel.AbstractChannelSubscriber;
import com.example.atcs.channel.EventMessage;
import com.example.atcs.data.cache.CacheKey;
import com.example.atcs.data.cache.CacheKeySerializer;
import com.example.atcs.data.queue.*;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

@Configuration
public class RedisConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("192.168.0.45", 6379);
//        configuration.setDatabase(0);
//        configuration.setUsername("username");
//        configuration.setPassword("password");

        final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(Duration.ofSeconds(10)).build();
        final ClientOptions clientOptions = ClientOptions.builder().socketOptions(socketOptions).build();

        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .clientOptions(clientOptions)
                .commandTimeout(Duration.ofSeconds(5))
                .shutdownTimeout(Duration.ZERO)
                .build();

        return new LettuceConnectionFactory(configuration, lettuceClientConfiguration);
    }
    @Bean(name = "gameQueueRedisTemplate")
    public RedisTemplate<GameQueueKey, GameQueueValue> gameQueueRedisTemplate() {
        RedisTemplate<GameQueueKey, GameQueueValue> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new GameQueueKeySerializer());
        redisTemplate.setValueSerializer(new GameQueueValueSerializer());
        return redisTemplate;
    }
    @Bean(name = "eventRedisTemplate")
    public RedisTemplate<String , String> eventRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(EventMessage.class));
        return redisTemplate;
    }
    @Bean(name = "gameQueueWaitingTimeRedisTemplate")
    public RedisTemplate<GameQueueKey, String> gameQueueWaitingTimeRedisTemplate() {
        RedisTemplate<GameQueueKey, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new GameQueueKeySerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
    @Bean(name = "messageQueueRedisTemplate")
    public RedisTemplate<MessageQueueKey, MessageQueueValue> messageQueueRedisTemplate() {
        RedisTemplate<MessageQueueKey, MessageQueueValue> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new MessageQueueKeySerializer());
        redisTemplate.setValueSerializer(new MessageQueueValueSerializer());
        return redisTemplate;
    }
    @Bean(name = "cacheRedisTemplate")
    public RedisTemplate<CacheKey, Long> cacheRedisTemplate() {
        RedisTemplate<CacheKey, Long> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new CacheKeySerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Long.class));
        return redisTemplate;
    }
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            List<AbstractChannelSubscriber> messageListeners
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        messageListeners.forEach(listener -> container.addMessageListener(listener, new ChannelTopic(listener.getTopic())));
        return container;
    }
    @Bean("increaseRequestCountRedisScript")
    public RedisScript<Long> increaseRequestCountRedisScript() {
        ClassPathResource scriptSource = new ClassPathResource("redis-scripts/increaseRequestCount.lua");
        return RedisScript.of(scriptSource, Long.class);
    }
    @Bean("decreaseRequestCountRedisScript")
    public RedisScript<Long> decreaseRequestCountRedisScript() {
        ClassPathResource scriptSource = new ClassPathResource("redis-scripts/decreaseRequestCount.lua");
        return RedisScript.of(scriptSource, Long.class);
    }
}
