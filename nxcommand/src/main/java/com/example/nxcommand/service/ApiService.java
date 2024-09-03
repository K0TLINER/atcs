package com.example.nxcommand.service;

import com.example.nxcommand.adapter.GameQueueAdapter;
import com.example.nxcommand.channel.Channel;
import com.example.nxcommand.channel.ChannelPublisher;
import com.example.nxcommand.data.info.Game;
import com.example.nxcommand.data.info.GameInfo;
import com.example.nxcommand.data.info.Server;
import com.example.nxcommand.data.info.Target;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final GameQueueAdapter gameQueueAdapter;
    private final ChannelPublisher channelPublisher;
    public void apiCall(GameInfo gameInfo, String memberId) {
        // api call code...
    }

    public void checkApiCall(GameInfo gameInfo, String memberId) {
        // redis 대기열 등록과 redis pub/sub publish 동작을 어떻게 하나의 트랜잭션으로 묶을 수 있을까?
        gameQueueAdapter.push(gameInfo, memberId);
        channelPublisher.publish(Channel.MESSAGE_REGISTER, gameInfo);
    }
}
