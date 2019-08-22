/*
 * @projectName spider
 * @package cn.stark.spider.starter
 * @className cn.stark.spider.starter.Application
 * @copyright Copyright 2019 Thuisoft, Inc. All rights reserved.
 */
package cn.stark.spider.starter;

import cn.stark.spider.common.spider.download.DownloadStarter;
import cn.stark.spider.common.spider.process.PageProcessStarter;
import cn.stark.spider.common.spider.scheduler.RedisScheduler;
import cn.stark.spider.common.utils.RedisConfig;
import cn.stark.spider.download.httpclient.HttpClientDownloader;
import cn.stark.spider.process.AutoProcess;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

/**
 * Application
 * @description TODO
 * @author liubolun
 * @date 2019年08月22日 13:56
 * @version 3.0.0
 */
public class Application {

    private final static String PAGE = "page";

    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create(RedisConfig.getConfig());
        PageProcessStarter pageProcessStarter = PageProcessStarter.create(new AutoProcess());
        pageProcessStarter.setPageScheduler(new RedisScheduler<>(PAGE, redissonClient));
        Thread thread = new Thread(() -> pageProcessStarter.run());
        thread.start();
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setScheduler(new RedisScheduler<>(PAGE, redissonClient));
        httpClientDownloader.setThread(2);
        DownloadStarter httpClientStarter = DownloadStarter.create(httpClientDownloader);
        httpClientStarter.setRequestScheduler(new RedisScheduler<>("request", redissonClient));
        httpClientStarter.run();
    }
}
