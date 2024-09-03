package com.example.nxcommand.data.info;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToGameInfoConverter implements Converter<String, GameInfo> {
    @Override
    public GameInfo convert(String source) {
        return GameInfo.fromString(source);
    }
}
