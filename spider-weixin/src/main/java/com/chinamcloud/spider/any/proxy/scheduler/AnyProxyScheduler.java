package com.chinamcloud.spider.any.proxy.scheduler;

import com.chinamcloud.spider.any.proxy.pojo.RequestParser;
import com.chinamcloud.spider.any.proxy.pojo.RequestUrl;
import com.chinamcloud.spider.any.proxy.pojo.WeChatRequest;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;
import static com.chinamcloud.spider.any.proxy._const.AnyProxyConst.*;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class AnyProxyScheduler extends DuplicateRemovedScheduler
        implements MonitorableScheduler, DuplicateRemover {

    private long integer = 1;

    private String[] queues = {LOAD_MORE_QUEUE, ANY_PROXY_QUEUE};

    public AnyProxyScheduler(JedisPool pool) {
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
        integer++;
        if (integer > 100000000000L) {
            integer = 0L;
        }
        String queue = queues[(int)(integer % queues.length)];
        Jedis jedis  = pool.getResource();
        try {
            Request request = null;
            // 从AnyProxy来的读取历史消息请求
            if (queue.equals(LOAD_MORE_QUEUE)) {
                String requestJson = jedis.lpop(queue);
                if (requestJson != null) {
                    WeChatRequest weChatRequest = gson.fromJson(requestJson, WeChatRequest.class);
                    request = RequestParser.parser(weChatRequest);
                    request.putExtra(QUEUE_TYPE, LOAD_MORE_QUEUE);
                    request.putExtra(URL_TYPE, LOAD_MORE);
                    RequestUrl url = new RequestUrl(request.getUrl());
                    url.setParam(OFFSET, "0");
                    request.putExtra(BIZ, url.getParam(BIZ));
                    long time = System.currentTimeMillis();
                    time = time / 1800000L;
                    if(jedis.sadd(LOAD_MORE_SET + url.getParam(BIZ), url.toUrl() + String.valueOf(time)) == 1) {
                        request.setUrl(url.toUrl());
                        return request;
                    }
                }
            }

            if (queue.equals(ANY_PROXY_QUEUE) || request == null) {
                String requestJson = jedis.lpop(ANY_PROXY_QUEUE);
                if (requestJson == null) {
                    return null;
                }
                request = gson.fromJson(requestJson, Request.class);
                return request;
            }

            return null;
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
                return jedis.sadd(setKey(request), request.getUrl()) == 0;
            }
            return false;
        } finally {
            jedis.close();
        }
    }

    private String setKey(Request request) {
        if (request.getExtra(URL_TYPE).equals(CONTENT)) {
            return ARTICLE_SET + request.getExtra(BIZ);
        } else if (request.getExtra(URL_TYPE).equals(LOAD_MORE)) {
            return LOAD_MORE_SET + request.getExtra(BIZ);
        } else {
            return null;
        }
    }

    @Override
    protected void pushWhenNoDuplicate(Request request, Task task) {
        Jedis jedis = pool.getResource();
        try {
            jedis.lpush(ANY_PROXY_QUEUE, gson.toJson(request));
        } finally {
            jedis.close();
        }
    }

    @Override
    public void resetDuplicateCheck(Task task) {

    }
}
