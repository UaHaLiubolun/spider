package cn.stark.spider.common.spider.process;

import cn.stark.spider.common.Page;
import cn.stark.spider.common.spider.PageProcesser;
import cn.stark.spider.common.spider.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PageProcessStarter implements Runnable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Scheduler<Page> pageScheduler;

    private ExecutorService executorService = Executors.newFixedThreadPool(12);

    private PageProcesser pageProcesser;

    public static PageProcessStarter create(PageProcesser pageProcesser) {
        PageProcessStarter pageProcessStarter = new PageProcessStarter();
        pageProcessStarter.pageProcesser = pageProcesser;
        return pageProcessStarter;
    }

    public PageProcessStarter setPageScheduler(Scheduler<Page> pageScheduler) {
        this.pageScheduler = pageScheduler;
        return this;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Page page = pageScheduler.poll();
                if (page == null) {
                    logger.info("没有任务需要分析了，我要睡觉了");
                    Thread.sleep(5000);
                } else {
                    executorService.execute(() -> pageProcesser.process(page));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
