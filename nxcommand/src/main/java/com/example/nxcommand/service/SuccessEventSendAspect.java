package com.example.nxcommand.service;

import com.example.nxcommand.channel.Channel;
import com.example.nxcommand.channel.ChannelPublisher;
import com.example.nxcommand.data.info.GameInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Aspect
@Slf4j
public class SuccessEventSendAspect {
    private final ChannelPublisher channelPublisher;

    public SuccessEventSendAspect(ChannelPublisher channelPublisher) {
        this.channelPublisher = channelPublisher;
    }

    @Around("execution(* com.example.nxcommand.service.ApiService.apiCall(..))")
    public void interceptors(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        GameInfo gameInfo = (GameInfo) args[0];
        String memberId = (String) args[1];
        log.info(String.format("api request %s for %s(memberId)", gameInfo, memberId));
        joinPoint.proceed();
        log.info(String.format("api request success %s for %s(memberId)", gameInfo, memberId));
        channelPublisher.publish(Channel.MESSAGE_SUCCESS, gameInfo);
    }
}
