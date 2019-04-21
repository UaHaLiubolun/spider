package com.chinamcloud.spider.pipeline;


import us.codecraft.webmagic.Task;

public interface ObjectPipeline {

    void process(Object o, Task task);
}
