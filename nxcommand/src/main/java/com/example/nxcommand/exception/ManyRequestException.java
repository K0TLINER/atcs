package com.example.nxcommand.exception;

import com.example.nxcommand.data.info.Game;
import org.springframework.http.HttpStatus;

public class ManyRequestException extends MyException {

    public ManyRequestException(Game game, String memberId) {
        super(HttpStatus.TOO_MANY_REQUESTS, "%s 서버가 혼잡합니다.".formatted(game), memberId);
    }
}
