package cn.stark.spider.common.spider.download;

import cn.stark.spider.common.Request;
import cn.stark.spider.common.spider.Downloader;
import cn.stark.spider.common.spider.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadStarter implements Runnable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Downloader downloader;

    private Scheduler<Request> requestScheduler;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public DownloadStarter(Downloader downloader) {
        this.downloader = downloader;
    }

    public static DownloadStarter create(Downloader downloader) {
        return new DownloadStarter(downloader);
    }

    public DownloadStarter setRequestScheduler(Scheduler<Request> scheduler) {
        this.requestScheduler = scheduler;
        return this;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = requestScheduler.poll();
                if (request == null) {
                    logger.info("暂时没有任务需要下载，睡觉了");
                    Thread.sleep(1000);
                    continue;
                }
                executorService.execute(() -> downloader.asyncDownload(request));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
