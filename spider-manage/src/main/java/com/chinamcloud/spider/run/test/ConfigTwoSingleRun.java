package com.chinamcloud.spider.run.test;

import com.chinamcloud.spider.ConfigTwoSpider;
import com.chinamcloud.spider.dao.SiteTaskDao;
import com.chinamcloud.spider.model.SiteTask;
import com.chinamcloud.spider.orm.redis.RedisPool;
import com.chinamcloud.spider.pipeline.MongoPipeline;
import com.chinamcloud.spider.scheduler.ConfigTwoRedisScheduler;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

import java.util.List;

public class ConfigTwoSingleRun {

    public static void main(String[] args) {
        SiteTaskDao siteTaskDao = new SiteTaskDao();

        SiteTask siteTask  = siteTaskDao.getByUrl("http://www.people.com.cn/");
        Spider configTwoSpider = ConfigTwoSpider.create(Site.me(), new MongoPipeline("newsTest"))
                .setScheduler(new ConfigTwoRedisScheduler(RedisPool.jedisPool()))
                .thread(1);
        Request request = new Request(siteTask.getUrl());
        request.setSourceUrl("Header");
        request.setExtractLinks(true);
        request.setInterval(siteTask.getInterval());
        request.putExtra("PageModel", siteTask.getPageModels());
        configTwoSpider.addRequest(request);
        configTwoSpider.setExitWhenComplete(true);
        configTwoSpider.run();
    }

}
