package com.chinamcloud.spider.search.scheduler;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;
import us.codecraft.webmagic.utils.UrlUtils;

public class SearchScheduler extends DuplicateRemovedScheduler implements MonitorableScheduler, DuplicateRemover {

    private static final String QUEUE_PREFIX = "queue_";

    private static final String SET_PREFIX = "set_";

    @Override
    public int getLeftRequestsCount(Task task) {
        return 0;
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return 0;
    }


    public SearchScheduler(JedisPool pool) {
        this.pool = pool;
        setDuplicateRemover(this);
    }

    private Gson gson;

    {
        gson = new Gson();
    }


    private JedisPool pool;

    @Override
    public Request poll(Task task) {
        Jedis jedis  = pool.getResource();
        try {
            String requestJson = jedis.lpop(QUEUE_PREFIX + "search");
            if (requestJson == null) {
                return null;
            }
            Request request = gson.fromJson(requestJson, Request.class);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }


    @Override
    protected void pushWhenNoDuplicate(Request request, Task task) {
        Jedis jedis = pool.getResource();
        try {
            jedis.rpush(QUEUE_PREFIX + "search", gson.toJson(request));
        } finally {
            jedis.close();
        }
    }

    @Override
    public boolean isDuplicate(Request request, Task task) {
        Jedis jedis = pool.getResource();
        try {
            if (!request.isExtractLinks()) {
                return jedis.sadd(getSetKey(request), request.getUrl()) == 0;
            }
            return false;
        } finally {
            jedis.close();
        }
    }

    @Override
    public void resetDuplicateCheck(Task task) {
        return;
    }

    protected String getSetKey(Request request) {
        return "search:" + SET_PREFIX + UrlUtils.getDomain(request.getUrl());
    }
}
