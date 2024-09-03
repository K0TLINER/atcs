package com.example.atcs.channel;

import com.example.atcs.adapter.CacheAdapter;
import com.example.atcs.adapter.GameQueueAdapter;
import com.example.atcs.data.cache.CurrentRequestCountCacheKey;
import com.example.atcs.data.info.GameInfo;
import com.example.atcs.data.queue.GameQueueKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SuccessMessageSubscriber extends AbstractChannelSubscriber{
    private final CacheAdapter cacheAdapter;
    private final GameQueueAdapter gameQueueAdapter;
    private final ChannelPublisher channelPublisher;

    public SuccessMessageSubscriber(CacheAdapter cacheAdapter, GameQueueAdapter gameQueueAdapter, ChannelPublisher channelPublisher) {
        this.cacheAdapter = cacheAdapter;
        this.gameQueueAdapter = gameQueueAdapter;
        this.channelPublisher = channelPublisher;
    }

    @Override
    public String getTopic() {
        return Channel.MESSAGE_SUCCESS.getTopic();
    }

    @Override
    public void onMessage(Channel channel, EventMessage eventMessage) {
        GameInfo gameInfo = eventMessage.getMessage();
        cacheAdapter.decrease(CurrentRequestCountCacheKey.from(gameInfo));
        Long count = gameQueueAdapter.size(GameQueueKey.from(gameInfo));
        if (count > 0) {
            channelPublisher.publish(Channel.REQUEST_DECREASE, gameInfo);
        }
    }
}
