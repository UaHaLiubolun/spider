package com.chinamcloud.spider.orm.redis;


import com.chinamcloud.spider.Const;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisPool {

    //Redis服务器IP
//    private static String ADDR = "r-2ze09f7b1e9f78b4.redis.rds.aliyuncs.com";
    private static String ADDR;
//    private static String ADDR = "172.29.4.24";
    //Redis的端口号
    private static Integer PORT = 6379;
    //访问密码
    private static String AUTH;
//    private static String AUTH = "";


    //可用连接实例的最大数目，默认为8；
    //如果赋值为-1，则表示不限制，如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
    private static Integer MAX_TOTAL = 10;
    //控制一个pool最多有多少个状态为idle(空闲)的jedis实例，默认值是8
    private static Integer MAX_IDLE = 10;
    //等待可用连接的最大时间，单位是毫秒，默认值为-1，表示永不超时。
    //如果超过等待时间，则直接抛出JedisConnectionException
    private static Integer MAX_WAIT_MILLIS = 10000;
    private static Integer TIMEOUT = 10000;
    //在borrow(用)一个jedis实例时，是否提前进行validate(验证)操作；
    //如果为true，则得到的jedis实例均是可用的
    private static Boolean TEST_ON_BORROW = true;
    private  static JedisPool jedisPool = null;

    static {
        if (Const.environment.equals("dev")) {
            ADDR = "172.29.4.69";
            AUTH = "";
        } else if (Const.environment.equals("prod")) {
            ADDR = "r-2ze09f7b1e9f78b4.redis.rds.aliyuncs.com";
            AUTH = "r-2ze09f7b1e9f78b4:Newmeds0bey";
        } else {
            ADDR = "localhost";
            AUTH = "r-2ze09f7b1e9f78b4:Newmeds0bey";
        }
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT_MILLIS);
            config.setTestOnBorrow(TEST_ON_BORROW);
            if (AUTH.equals("")) {
                jedisPool = new JedisPool(config,ADDR,PORT,TIMEOUT);
            } else {
                jedisPool = new JedisPool(config,ADDR,PORT,TIMEOUT, AUTH, 3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis(){
        try {
            if(jedisPool != null){
                Jedis jedis = jedisPool.getResource();
                return jedis;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized static JedisPool jedisPool() {
        return jedisPool;
    }

    /**
     * 释放Jedis
     * @param jedis
     */
    public static void returnResource(final Jedis jedis){
        //方法参数被声明为final，表示它是只读的。
        if(jedis!=null){
            jedis.close();
        }
    }
}
