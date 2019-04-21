package com.chinamcloud.spider.run;

import com.chinamcloud.spider.ConfigTwoSpider;
import com.chinamcloud.spider.Const;
import com.chinamcloud.spider.orm.redis.RedisPool;
import com.chinamcloud.spider.pipeline.KafakaPipeline;
import com.chinamcloud.spider.pipeline.MongoPipeline;
import com.chinamcloud.spider.scheduler.ConfigTwoRedisScheduler;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

public class SpiderPoolRun {

    public static void main(String[] args) {
        if (args.length <= 0) {
            System.out.println("Args error");
            return;
        }

        ConfigTwoSpider configTwoSpider = ConfigTwoSpider.create(Site.me().setDomain("PoolOne"),
                new MongoPipeline("spider_news"));
        if (Const.environment.equals("prod")) {
            configTwoSpider.putConfigPipeline(new KafakaPipeline());
        }
        configTwoSpider.setScheduler(new ConfigTwoRedisScheduler(RedisPool.jedisPool()))
                .thread(Integer.parseInt(args[0]));
        configTwoSpider.setExitWhenComplete(false);
        configTwoSpider.run();
    }
}
