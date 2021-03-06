package com.qiding.redis.api;

import java.time.Duration;

public interface IRedis {
    String  getValue(String key);

    void setValue(String key, String value, Duration duration);

}
