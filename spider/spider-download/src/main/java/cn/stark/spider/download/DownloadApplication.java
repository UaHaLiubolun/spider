package cn.stark.spider.download;

import cn.stark.spider.common.spider.download.DownloadStarter;
import cn.stark.spider.common.spider.scheduler.RedisScheduler;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

public class DownloadApplication {

    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create();
        DownloadStarter downloadStarter = DownloadStarter.create(new OkHttpDownloader(new RedisScheduler<>("page", redissonClient)));
        downloadStarter.setRequestScheduler(new RedisScheduler<>("request", redissonClient));
        downloadStarter.run();
    }
}
