package com.example.atcs.channel;

import com.example.atcs.service.ATCSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageRegisterSubscriber extends AbstractChannelSubscriber {
    private final ATCSService atcsService;

    public MessageRegisterSubscriber(ATCSService atcsService) {
        this.atcsService = atcsService;
    }

    @Override
    public String getTopic() {
        return Channel.MESSAGE_REGISTER.getTopic();
    }

    @Override
    public void onMessage(Channel channel, EventMessage eventMessage) {
        atcsService.sendMessage(eventMessage);
    }
}
