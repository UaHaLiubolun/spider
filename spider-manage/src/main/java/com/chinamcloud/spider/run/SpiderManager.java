package com.chinamcloud.spider.run;

import com.chinamcloud.spider.Const;
import com.chinamcloud.spider.dao.NewsListDao;
import com.chinamcloud.spider.dao.SearchKeywordDao;
import com.chinamcloud.spider.dao.SiteTaskDao;
import com.chinamcloud.spider.model.SiteTask;
import com.chinamcloud.spider.orm.redis.RedisUtil;
import com.chinamcloud.spider.search.util.RequestGenerate;
import com.chinamcloud.spider.search.util.Site;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.HttpRequestBody;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpiderManager implements Runnable{

    private SiteTaskDao siteTaskDao;

    private NewsListDao newsListDao;

    private SearchKeywordDao searchKeywordDao;

    {
        siteTaskDao = new SiteTaskDao();
        newsListDao = new NewsListDao();
        searchKeywordDao = new SearchKeywordDao();
    }


    @Override
    public void run() {
        List<SiteTask> siteTasks = siteTaskDao.getOn();
        List<SiteTask> canRun = getCanRun(siteTasks);
        writeToRedis(canRun);
        updateLastTime(canRun);

        if (Const.search_manager) {
            searchKwManger();
        }
    }

    private void searchKwManger() {
        List<Map<String, Object>> maps = searchKeywordDao.getAll();
        List<Map<String, Object>> canMaps = new LinkedList<>();
        Date date = new Date();
        for (Map<String, Object> s : maps) {
            int i = Integer.parseInt(s.get("interval").toString()) + Integer.parseInt(s.get("lastTime").toString());
            boolean isCanRun = (i - (date.getTime()/1000)) < 0;
            if (isCanRun) {
                canMaps.add(s);
            }
        }
        for (Map<String, Object> s : canMaps) {
            writeToRedis(s.get("kw").toString());
        }
        updateSearchLastTime(canMaps);
    }


    private List<SiteTask> getCanRun(List<SiteTask> siteTasks) {
        Date date = new Date();
        List<SiteTask> runSiteTask = new LinkedList<>();
        for (SiteTask s : siteTasks) {
            int i = s.getInterval() + s.getLastTime();
            boolean isCanRun = (i - (date.getTime()/1000)) < 0;
            if (isCanRun && s.getSpiderType().equals("ListSpider")) {
                runSiteTask.add(s);
            }
        }
        return runSiteTask;
    }

    private void writeToRedis(List<SiteTask> siteTasks) {
        for (SiteTask siteTask:
             siteTasks) {
            Request request = new Request(tranUrl(siteTask.getUrl()));
            request.setMethod(siteTask.getMethod());
            if (!siteTask.getMethod().equalsIgnoreCase("get")) {
                tranRequestBody(request, siteTask);
            }
            request.setSourceUrl("Header");
            request.setExtractLinks(true);
            request.setInterval(siteTask.getInterval());
            request.putExtra("PageModel", siteTask.getPageModels());
            request.putExtra("source", siteTask.getSource());
            request.putExtra("type", siteTask.getType());
            request.setCharset(siteTask.getCharset());
            request.setTest(siteTask.isTest());
            request.putExtra("sourceName", newsListDao.getNameByUrl(siteTask.getUrl()));
            RedisUtil.pushRequest("queue_spider_list", request);
        }
    }

    private void tranRequestBody(Request request, SiteTask siteTask) {
        if (siteTask.getContentType().equals(HttpRequestBody.ContentType.JSON)) {
            request.setRequestBody(HttpRequestBody.json(siteTask.getBody(), siteTask.getCharset()));
        } else if (siteTask.getContentType().equals(HttpRequestBody.ContentType.FORM)) {
            request.setRequestBody(HttpRequestBody.form(tranFormData(siteTask.getBody()), siteTask.getCharset()));
        }
    }

    private Map<String, Object> tranFormData(String content) {
        String[] ss = content.split("&");
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < ss.length; i++) {
            String[] m = ss[i].split("=");
            map.put(m[0], m[1]);
        }
        return map;
    }

    private void writeToRedis(String s) {
        String[] ss = s.split(",");
        for (String sss : ss) {
            RedisUtil.pushRequest("queue_search", RequestGenerate.site(Site.SO_GOU, sss));
            RedisUtil.pushRequest("queue_search", RequestGenerate.site(Site.BAI_DU, sss));
        }
    }


    private String tranUrl(String url) {
        if (url.contains("{{TimeStamp}}")) {
            url = url.replace("{{TimeStamp}}", String.valueOf(new Date().getTime()));
        }
        return url;
    }

    private void updateLastTime(List<SiteTask> siteTasks) {
        String[] urls = new String[siteTasks.size()];
        for (int i = 0; i < siteTasks.size(); i++) {
            urls[i] = siteTasks.get(i).getUrl();
        }
        siteTaskDao.updateLastTimes(urls);
    }

    private void updateSearchLastTime(List<Map<String, Object>> maps) {
        String[] urls = new String[maps.size()];
        for (int i = 0; i < maps.size(); i++) {
            urls[i] = maps.get(i).get("kw").toString();
        }
        searchKeywordDao.updateLastTimes(urls);
    }

    public static void main(String[] args) {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
        schedule.scheduleAtFixedRate(new SpiderManager(), 1, 30, TimeUnit.SECONDS);
    }
}
