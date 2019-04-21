package com.chinamcloud.spider.search;

import com.chinamcloud.spider.Const;
import com.chinamcloud.spider.orm.redis.RedisPool;
import com.chinamcloud.spider.search.pipeline.SearchKafkaPipeline;
import com.chinamcloud.spider.search.pipeline.SearchPipeline;
import com.chinamcloud.spider.search.process.Process;
import com.chinamcloud.spider.search.scheduler.SearchScheduler;
import com.chinamcloud.spider.search.util.RequestGenerate;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

public class SearchSpider {

    public static void main(String[] args) {
        Spider spider = Spider.create(new Process(Site.me().setCharset(null))).thread(
                Integer.parseInt(args[0])
        ).addPipeline(new SearchPipeline());
        if (Const.environment.equals("prod")) {
            spider.addPipeline(new SearchKafkaPipeline());
        }
        spider.setScheduler(new SearchScheduler(RedisPool.jedisPool()));
        spider.setExitWhenComplete(false);
        spider.run();
    }
}
