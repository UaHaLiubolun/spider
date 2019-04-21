package com.chinamcloud.spider.weixin.scheduler;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

public class WeChatSecheduler extends DuplicateRemovedScheduler
        implements MonitorableScheduler, DuplicateRemover {


    private static final String QUEUE_PREFIX = "queue_";

    private static final String SET_PREFIX = "set_";

    public WeChatSecheduler(JedisPool pool) {
        this.pool = pool;
        setDuplicateRemover(this);
    }

    private Gson gson;

    {
        gson = new Gson();
    }


    private JedisPool pool;


    @Override
    public int getLeftRequestsCount(Task task) {
        return 0;
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return 0;
    }

    @Override
    public Request poll(Task task) {
        Jedis jedis  = pool.getResource();
        try {
            String requestJson = jedis.lpop(QUEUE_PREFIX + "gzh_we_chat");
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
    public boolean isDuplicate(Request request, Task task) {
        Jedis jedis = pool.getResource();
        try {
            if (!request.isExtractLinks()) {
                String date = request.getExtra("contentId").toString();
                String gzhId = request.getExtra("gzhId").toString();
                return jedis.sadd(setKey(request), gzhId + date) == 0;
            }
            return false;
        } finally {
            jedis.close();
        }
    }


    @Override
    protected void pushWhenNoDuplicate(Request request, Task task) {
        Jedis jedis = pool.getResource();
        try {
            jedis.rpush(QUEUE_PREFIX + "gzh_we_chat", gson.toJson(request));
        } finally {
            jedis.close();
        }
    }

    private String setKey(Request request) {
        return "gzh:" + SET_PREFIX + request.getExtra("gzhId");
    }

    @Override
    public void resetDuplicateCheck(Task task) {
        return;
    }
}
