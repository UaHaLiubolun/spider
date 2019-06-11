package cn.stark.spider.common.spider.download;

import cn.stark.spider.common.Page;
import cn.stark.spider.common.Request;
import cn.stark.spider.common.spider.Downloader;
import cn.stark.spider.common.spider.Scheduler;

public class DownloadStarter implements Runnable {

    private Downloader downloader;

    private Scheduler<Request> requestScheduler;

    private Scheduler<Page> pageScheduler;

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

    public DownloadStarter setPageScheduler(Scheduler<Page> scheduler) {
        this.pageScheduler = scheduler;
        return this;
    }

    @Override
    public void run() {

    }
}
