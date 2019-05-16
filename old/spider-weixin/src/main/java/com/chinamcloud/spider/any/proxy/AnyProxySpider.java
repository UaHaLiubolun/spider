package com.chinamcloud.spider.any.proxy;

import com.chinamcloud.spider.Const;
import com.chinamcloud.spider.any.proxy.process.AnyProxyProcess;
import com.chinamcloud.spider.any.proxy.provider.AnyProxyProvider;
import com.chinamcloud.spider.any.proxy.scheduler.AnyProxyScheduler;
import com.chinamcloud.spider.orm.redis.RedisPool;
import com.chinamcloud.spider.weixin.pipeline.HttpPipeline;
import com.chinamcloud.spider.weixin.pipeline.KafakaPipeline;
import com.chinamcloud.spider.weixin.pipeline.WeiXinPipeline;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

public class AnyProxySpider {

    public static void main(String[] args) {

        int threadNum = 20;
        if (args.length > 0) {
            threadNum = Integer.parseInt(args[0]);
        }

        Downloader downloader = new HttpClientDownloader();
        ((HttpClientDownloader) downloader).setProxyProvider(new AnyProxyProvider());
        Spider spider = Spider
                .create(new AnyProxyProcess(Site.me()))
                .thread(threadNum)
                .addPipeline(new WeiXinPipeline())
                .setDownloader(downloader);
        if (Const.environment.equals("prod")) {
            spider.addPipeline(new KafakaPipeline());
        } else {
            spider.addPipeline(new HttpPipeline());
        }
        spider.setScheduler(new AnyProxyScheduler(RedisPool.jedisPool()));
        spider.setExitWhenComplete(false);
        spider.run();
    }
}
