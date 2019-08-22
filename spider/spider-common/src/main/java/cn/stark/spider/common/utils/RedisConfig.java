package cn.stark.spider.common.utils;

import org.redisson.config.Config;

public class RedisConfig {

    public static Config getConfig() {
        Config config = new Config();
        config.setNettyThreads(1);
        config.useSingleServer().setAddress("redis://132.232.103.174:6379");
        return config;
    }
}
