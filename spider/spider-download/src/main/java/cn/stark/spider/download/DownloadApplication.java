package cn.stark.spider.download;

import cn.stark.spider.common.spider.download.DownloadStarter;
import cn.stark.spider.common.spider.scheduler.RedisScheduler;
import cn.stark.spider.common.utils.RedisConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

public class DownloadApplication {

    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create(RedisConfig.getConfig());
        DownloadStarter downloadStarter = DownloadStarter.create(new OkHttpDownloader(new RedisScheduler<>("page", redissonClient)));
        downloadStarter.setRequestScheduler(new RedisScheduler<>("request", redissonClient));
        downloadStarter.run();
    }
}
