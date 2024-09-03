package com.example.atcs.service;

import com.example.atcs.adapter.GameQueueAdapter;
import com.example.atcs.adapter.MessageQueueAdapter;
import com.example.atcs.channel.Channel;
import com.example.atcs.channel.ChannelPublisher;
import com.example.atcs.channel.EventMessage;
import com.example.atcs.data.queue.GameQueueKey;
import com.example.atcs.data.queue.GameQueueValue;
import com.example.atcs.data.queue.MessageQueueValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ATCSService {
    private final GameQueueAdapter gameQueueAdapter;
    private final MessageQueueAdapter messageQueueAdapter;
    private final ChannelPublisher channelPublisher;

    public ATCSService(GameQueueAdapter gameQueueAdapter, MessageQueueAdapter messageQueueAdapter, ChannelPublisher channelPublisher) {
        this.gameQueueAdapter = gameQueueAdapter;
        this.messageQueueAdapter = messageQueueAdapter;
        this.channelPublisher = channelPublisher;
    }

    public void sendMessage(EventMessage eventMessage) {
        GameQueueValue value = gameQueueAdapter.pop(GameQueueKey.from(eventMessage.getMessage()));
        messageQueueAdapter.push(MessageQueueValue.of(eventMessage.getMessage(), value.getMemberId()));
        channelPublisher.publish(Channel.MESSAGE_SEND, null);
    }
}
