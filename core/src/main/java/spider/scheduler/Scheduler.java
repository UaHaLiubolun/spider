package spider.scheduler;

import spider.Task;

public interface Scheduler {


    void push(Task task);

    Task poll();

}
