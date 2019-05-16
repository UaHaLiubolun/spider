package com.chinamcloud.spider.run;

import com.chinamcloud.spider.dao.WeChatDao;
import com.chinamcloud.spider.orm.redis.RedisUtil;
import com.chinamcloud.spider.weixin.util.WeiXinRequestGenerate;
import us.codecraft.webmagic.Request;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeChatSpiderManager implements Runnable{

    private WeChatDao weChatDao;


    {
        weChatDao = new WeChatDao();
    }


    @Override
    public void run() {
        List<Map<String, String>> gzhList = weChatDao.getOn();
        gzhList.stream().forEach(
                s -> System.out.println(s.get("name") + ": " + Math.abs(s.get("gzhId").hashCode()))
        );
        List<Map<String, String>> canRun = getCanRun(gzhList);
        writeToRedis(canRun);
        updateLastTime(canRun);
    }


    private List<Map<String, String>> getCanRun(List<Map<String, String>> siteTasks) {
        Date date = new Date();
        List<Map<String, String>> runSiteTask = new LinkedList<>();
        for (Map<String, String> s : siteTasks) {
            int i = Integer.parseInt(s.get("interval")) + Integer.parseInt(s.get("lastTime"));
            boolean isCanRun = (i - (date.getTime()/1000)) < 0;
            if (isCanRun) {
                runSiteTask.add(s);
            }
        }
        return runSiteTask;
    }

    private void writeToRedis(List<Map<String, String>> siteTasks) {
        for (Map<String, String> siteTask:
                siteTasks) {
            Request request = null;
            if (siteTask.get("searchType") != null) {
                if (siteTask.get("searchType").equals("kw")) {
                    String kw = siteTask.get("kw");
                    request = WeiXinRequestGenerate.kwRequest(kw);
                }
            } else {
                request = WeiXinRequestGenerate.searchRequest(siteTask.get("gzhId"),
                        siteTask.get("type"), siteTask.get("name"));
            }
            if (request != null) {
                RedisUtil.pushRequest("queue_gzh_we_chat", request);
            }
        }
    }

    private void updateLastTime(List<Map<String, String>> siteTasks) {
        String[] urls = new String[siteTasks.size()];
        for (int i = 0; i < siteTasks.size(); i++) {
            urls[i] = siteTasks.get(i).get("gzhId");
        }
        weChatDao.updateLastTimes(urls);
    }

    public static void main(String[] args) {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
        schedule.scheduleAtFixedRate(new WeChatSpiderManager(), 1, 30, TimeUnit.SECONDS);
    }
}
