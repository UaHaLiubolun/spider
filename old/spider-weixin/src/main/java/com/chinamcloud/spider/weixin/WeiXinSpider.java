package com.chinamcloud.spider.weixin;

import com.chinamcloud.spider.Const;
import com.chinamcloud.spider.orm.redis.RedisPool;
import com.chinamcloud.spider.weixin.download.WeChatDownloader;
import com.chinamcloud.spider.weixin.download.v2.WeChatHttpDownloader;
import com.chinamcloud.spider.weixin.pipeline.HttpPipeline;
import com.chinamcloud.spider.weixin.pipeline.KafakaPipeline;
import com.chinamcloud.spider.weixin.pipeline.WeiXinPipeline;
import com.chinamcloud.spider.weixin.process.WeiXinProcess;
import com.chinamcloud.spider.weixin.scheduler.WeChatSecheduler;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;

public class WeiXinSpider {

    public static void main(String[] args) {

        if (args.length <= 0) {
            System.out.println("Args error");
            return;
        }

        Downloader downloader = new WeChatHttpDownloader();
        Spider spider = Spider.create(new WeiXinProcess(Site.me())).thread(
                Integer.parseInt(args[0])
        ).addPipeline(new WeiXinPipeline()).setDownloader(downloader);
        if (Const.environment.equals("prod")) {
            spider.addPipeline(new KafakaPipeline());
        } else {
            spider.addPipeline(new HttpPipeline());
        }
        spider.setScheduler(new WeChatSecheduler(RedisPool.jedisPool()));
        spider.setExitWhenComplete(false);
        spider.run();
    }
}
