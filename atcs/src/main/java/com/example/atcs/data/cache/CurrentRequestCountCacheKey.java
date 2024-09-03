package com.example.atcs.data.cache;

import com.example.atcs.data.info.GameInfo;

public class CurrentRequestCountCacheKey extends CacheKey{
    @Override
    protected String getPostfix() {
        return "current_request_count";
    }
    private CurrentRequestCountCacheKey(GameInfo gameInfo) {
        super(gameInfo.getGame(), gameInfo.getTarget(), gameInfo.getServer());
    }
    public static CacheKey from(GameInfo gameInfo) {
        return new CurrentRequestCountCacheKey(gameInfo);
    }
}
