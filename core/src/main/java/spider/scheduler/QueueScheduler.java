package spider.scheduler;

import spider.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueScheduler implements Scheduler {

    private BlockingQueue<Task> queue = new LinkedBlockingQueue<Task>();


    public int getSize() {
        return queue.size();
    }

    public void push(Task task) {
        queue.add(task);
    }

    public Task poll() {
        return queue.poll();
    }
}
