package com.example.nxcommand.data.info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class GameInfo {
    private final static String DELIMITER = ":";
    private Game game;
    private Target target;
    private Server server;
    private GameInfo(String game, String target, String server) {
        this.game = Game.forValue(game);
        this.target = Target.forValue(target);
        this.server = Server.forValue(server);
    }

    @Override
    public String toString() {
        return new StringBuilder(game.getTitle())
                .append(DELIMITER)
                .append(target.getGroup())
                .append(DELIMITER)
                .append(server.getTitle())
                .toString();
    }
    public static GameInfo fromString(String str) {
        try {
            String[] infos = str.split(DELIMITER);
            return new GameInfo(infos[0], infos[1], infos[2]);
        } catch (Exception e) {
            log.error("[ERROR] " + str + " 게임 식별자를 확인해주세요.");
            throw new IllegalArgumentException();
        }
    }
    @JsonCreator
    public static GameInfo of(
            @JsonProperty("game") Game game,
            @JsonProperty("target") Target target,
            @JsonProperty("server") Server server) {
        return new GameInfo(game.getTitle(), target.getGroup(), server.getTitle());
    }
}
