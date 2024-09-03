local currentKey = KEYS[1]
local current = tonumber(redis.call('get', currentKey) or '0')
if current - 1 < 0 then
    return 0
else
    redis.call('DECRBY', currentKey, '1')
    return current - 1

end