package com.chinamcloud.spider.orm.redis;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.Request;

public class RedisUtil {

    private static Jedis jedis;

    private static Gson gson;

    static {
        jedis = RedisPool.getJedis();
        gson = new Gson();
    }

    public static Jedis jedis() {
        return jedis;
    }

    public static void pushList(String name, String value) {
        jedis.rpush(name, value);
    }

    public static String lpop(String name) {
        return jedis.lpop(name);
    }


    public static void pushRequest(String name, Request request) {
        jedis.rpush(name, gson.toJson(request));
    }


}
