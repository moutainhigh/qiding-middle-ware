package com.qiding.demo.api;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;

public interface IRedis {
    String  getValue(String key);

    void setValue(String key, String value, Duration duration);

}
