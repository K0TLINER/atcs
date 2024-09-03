local maxKey = KEYS[1]
local currentKey = KEYS[2]
local max = tonumber(redis.call('get', maxKey) or '-1')
local current = tonumber(redis.call('get', currentKey) or '0')
if current + 1 > max then
    return 0

else
    redis.call('INCRBY', currentKey, '1')
    return current + 1

end