package com.example.atcs.data.cache;

import com.example.atcs.data.info.GameInfo;

public class MaxConcurrencyCacheKey extends CacheKey{
    @Override
    protected String getPostfix() {
        return "max_concurrency";
    }
    private MaxConcurrencyCacheKey(GameInfo gameInfo) {
        super(gameInfo.getGame(), gameInfo.getTarget(), gameInfo.getServer());
    }
    public static CacheKey from(GameInfo gameInfo) {
        return new MaxConcurrencyCacheKey(gameInfo);
    }
}
