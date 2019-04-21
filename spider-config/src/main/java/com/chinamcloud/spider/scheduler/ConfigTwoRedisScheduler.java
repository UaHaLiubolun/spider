package com.chinamcloud.spider.scheduler;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;
import us.codecraft.webmagic.utils.UrlUtils;

import java.util.Date;

public class ConfigTwoRedisScheduler
        extends DuplicateRemovedScheduler
        implements MonitorableScheduler,DuplicateRemover {

    private static final String QUEUE_PREFIX =  "queue_";

    private static final String SET_PREFIX = "set:news:set_";

    private Gson gson;

    {
        gson = new Gson();
    }


    private JedisPool pool;

    public ConfigTwoRedisScheduler(String host) {
        this(new JedisPool(new JedisPoolConfig(), host));
    }

    public ConfigTwoRedisScheduler(JedisPool pool) {
        this.pool = pool;
        setDuplicateRemover(this);
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return 0;
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        Jedis jedis = pool.getResource();
        try {
            Long size = jedis.scard(getSetKey(task));
            return size.intValue();
        } finally {
            jedis.close();
        }
    }

    @Override
    public Request poll(Task task) {
        Jedis jedis = pool.getResource();
        try {
            String requestJson = jedis.lpop(getQueueKey(task));
            if (requestJson == null) {
                return null;
            }
            Request request = gson.fromJson(requestJson, Request.class);
            return request;
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean isDuplicate(Request request, Task task) {
        if (request.isTest()) {
            return false;
        }
        Jedis jedis = pool.getResource();
        try {
            if (!request.isExtractLinks()) {
                return jedis.sadd(getSetKey(request), request.getUrl()) == 0;
            } else {
                Date date = new Date();
                long i = date.getTime() / (request.getInterval() * 1000);
                return jedis.sadd(getSetKey(request), request.getUrl() + i) == 0;
            }
        } finally {
            jedis.close();
        }
    }

    @Override
    public void resetDuplicateCheck(Task task) {
       return;
    }

    @Override
    protected void pushWhenNoDuplicate(Request request, Task task) {
        Jedis jedis = pool.getResource();
        try {
            jedis.rpush(getQueueKey(task), gson.toJson(request));
        } finally {
            jedis.close();
        }
    }

    protected String getQueueKey(Task task) {
        return QUEUE_PREFIX + "spider_list";
    }

    protected String getSetKey(Task task) {
        return SET_PREFIX + task.getUUID();
    }

    protected String getSetKey(Request request) {
        return SET_PREFIX + UrlUtils.getDomain(request.getUrl());
    }
}
