package com.example.atcs.data.cache;

import com.example.atcs.data.info.Game;
import com.example.atcs.data.info.GameInfo;
import com.example.atcs.data.info.Server;
import com.example.atcs.data.info.Target;

public abstract class CacheKey {
    private final static String PREFIX = "game_server_id:";
    private static final String DELIMITER = ":";
    private static final String MAX_CONCURRENCY = "max_concurrency";
    private static final String CURRENT_REQUEST_COUNT = "current_request_count";
    private Game game;
    private Target target;
    private Server server;
    protected abstract String getPostfix();

    protected CacheKey(Game game, Target target, Server server) {
        this.game = game;
        this.target = target;
        this.server = server;
    }
    public static CacheKey fromString(String key) {
        String[] tokens = key.split(DELIMITER);
        Game game = Game.forValue(tokens[1]);
        Target target = Target.forValue(tokens[2]);
        Server server = Server.forValue(tokens[3]);
        if (key.endsWith(MAX_CONCURRENCY)) {
            return MaxConcurrencyCacheKey.from(GameInfo.of(game, target, server));
        } else if (key.endsWith(CURRENT_REQUEST_COUNT)) {
            return CurrentRequestCountCacheKey.from(GameInfo.of(game, target, server));
        } else {
            throw new IllegalArgumentException();
        }
    }
    @Override
    public String toString() {
        return new StringBuilder(PREFIX)
                .append(game.getTitle())
                .append(DELIMITER)
                .append(target.getGroup())
                .append(DELIMITER)
                .append(server.getTitle())
                .append(DELIMITER)
                .append(getPostfix())
                .toString();
    }
}
