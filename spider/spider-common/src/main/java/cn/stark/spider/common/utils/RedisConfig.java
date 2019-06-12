package cn.stark.spider.common.utils;

import org.redisson.config.Config;

public class RedisConfig {

    public static Config getConfig() {
        Config config = new Config();
        config.setNettyThreads(1);
        config.useSingleServer().setAddress("redis://localhost:6379");
        return config;
    }
}
