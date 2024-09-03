package com.example.atcs.data.queue;

import com.example.atcs.data.info.Game;
import com.example.atcs.data.info.GameInfo;
import com.example.atcs.data.info.Server;
import com.example.atcs.data.info.Target;
import java.util.Objects;

public class GameQueueKey {
    private static final String PREFIX = "atcs_message_queue_id:";
    private static final String DELIMITER = ":";
    private static final String WAITING_TIME_SUFFIX = "queue_waiting_time_ms";
    private Game game;
    private Target target;
    private Server server;
    private boolean isWaiting = false;

    public Game getGame() {
        return game;
    }
    private GameQueueKey(Game game, Target target, Server server) {
        if (Objects.isNull(game)) {
            throw new IllegalArgumentException("game can't be null");
        }
        this.game = game;
        this.target = target;
        this.server = server;
    }
    public static GameQueueKey from(GameInfo gameInfo) {
        Game game = gameInfo.getGame();
        Target target = gameInfo.getTarget();
        Server server = gameInfo.getServer();
        return new GameQueueKey(game, target, server);
    }
    public static GameQueueKey fromString(String key) {
        GameInfo gameInfo = GameInfo.fromString(key.substring(PREFIX.length()));
        return GameQueueKey.from(gameInfo);
    }
    @Override
    public String toString() {
        return new StringBuilder(PREFIX)
                .append(game.getTitle())
                .append(DELIMITER)
                .append(target.getGroup())
                .append(DELIMITER)
                .append(server.getTitle())
                .append(isWaiting ? DELIMITER + WAITING_TIME_SUFFIX : "")
                .toString();
    }
    public  GameQueueKey getWaitingKey() {
        GameQueueKey waitingKey = fromString(this.toString());
        waitingKey.isWaiting = true;
        return waitingKey;
    }
}
