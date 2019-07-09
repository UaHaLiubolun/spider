package cn.stark.spider.download;

import cn.stark.spider.common.spider.download.DownloadStarter;
import cn.stark.spider.common.spider.scheduler.RedisScheduler;
import cn.stark.spider.common.utils.RedisConfig;
import cn.stark.spider.download.httpclient.HttpClientDownloader;
import cn.stark.spider.download.proxy.SingleProxyProvider;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

public class DownloadApplication {

    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create(RedisConfig.getConfig());
//        DownloadStarter downloadStarter = DownloadStarter.create(new OkHttpDownloader(new RedisScheduler<>("page", redissonClient)));
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setScheduler(new RedisScheduler<>("page", redissonClient));
        httpClientDownloader.setThread(10);
//        httpClientDownloader.setProxyProvider(new SingleProxyProvider());
        DownloadStarter httpClientStarter = DownloadStarter.create(httpClientDownloader);
        httpClientStarter.setRequestScheduler(new RedisScheduler<>("request", redissonClient));
        httpClientStarter.run();
    }
}
