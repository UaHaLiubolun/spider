package spider;

import spider.downloader.Downloader;
import spider.scheduler.QueueScheduler;
import spider.scheduler.Scheduler;
import spider.thread.CountableThreadPool;
import java.util.Date;
import java.util.List;

public class Spider implements Runnable{

    private Downloader downloader;

    private QueueScheduler scheduler = new QueueScheduler();

    private int threadNum;

    private Site site;

    private CountableThreadPool threadPool;

    private List<Request> startRequests;

    private Date startTime;


    public Spider(int threadNum, Site site, List<Request> startRequests) {
        this.threadNum = threadNum;
        this.startRequests = startRequests;
        this.site = site;
    }

    private void initComponent() {
        if (downloader == null) this.downloader = new Downloader();
        downloader.setThread(threadNum);
        if (threadPool == null) {
            threadPool = new CountableThreadPool(threadNum);
        }
        if (startRequests != null) {
            for (Request request : startRequests) {
                Task task = new Task(site, request);
                addTask(task);
            }
            startRequests.clear();
        }
        startTime = new Date();
    }

    public void run() {
        initComponent();
        while (!Thread.currentThread().isInterrupted()) {
            final Task task = scheduler.poll();
            System.out.println(threadPool.getThreadAlive());
            if (task == null && threadPool.getThreadAlive().get() == 0) {
                threadPool.shutdown();
                // 判断是否时停止
                break;
            } else {
                threadPool.execute(new Runnable() {
                    public void run() {
                        try {
                            processTask(task);
                        } catch (Exception e) {

                        } finally {

                        }
                    }
                });
            }
        }
    }


    private void processTask(Task task) {
        Page page = downloader.downloader(task);
        onDownloadSuccess(task, page);
    }

    private void onDownloadSuccess(Task task, Page page) {
        task.getRequest().getFilter().filter(page);
        addPageTask(page);
    }

    private void addPageTask(Page page) {
        for (Request request : page.getNewRequests()) {
            Task task = new Task(site, request);
            addTask(task);
        }
    }

    private void addTask(Task task) {
        scheduler.push(task);
    }
}



