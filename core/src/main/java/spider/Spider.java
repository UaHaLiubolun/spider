package spider;

import spider.downloader.Downloader;
import spider.scheduler.QueueScheduler;
import spider.scheduler.Scheduler;

public class Spider implements Runnable{

    private Downloader downloader;

    private Site site;

    private Scheduler scheduler = new QueueScheduler();

    public void run() {

    }
}




