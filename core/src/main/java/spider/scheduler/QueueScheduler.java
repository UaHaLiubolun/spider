package spider.scheduler;

import spider.Task;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueScheduler implements Scheduler {

    private BlockingQueue<Task> queue = new LinkedBlockingQueue<Task>();

    private Set<String> urls = new HashSet<String>();

    public int getSize() {
        return queue.size();
    }

    public void push(Task task) {
        if (urls.contains(task.getRequest().getUrl())) return;
        urls.add(task.getRequest().getUrl());
        queue.add(task);
    }

    public Task poll() {
        return queue.poll();
    }
}
