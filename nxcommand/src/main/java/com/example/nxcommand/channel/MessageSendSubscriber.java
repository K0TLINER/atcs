package com.example.nxcommand.channel;

import com.example.nxcommand.adapter.MessageQueueAdapter;
import com.example.nxcommand.data.queue.MessageQueueValue;
import com.example.nxcommand.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class MessageSendSubscriber extends AbstractChannelSubscriber {
    private final ApiService apiService;
    private final MessageQueueAdapter messageQueueAdapter;
    public MessageSendSubscriber(ApiService apiService, MessageQueueAdapter messageQueueAdapter) {
        this.apiService = apiService;
        this.messageQueueAdapter = messageQueueAdapter;
    }

    @Override
    public void onMessage(Channel channel, EventMessage eventMessage) {
        MessageQueueValue value = messageQueueAdapter.leftPop();
        apiService.apiCall(value.getGameInfo(), value.getMemberId());
    }

    @Override
    public String getTopic() {
        return Channel.MESSAGE_SEND.getTopic();
    }
}
