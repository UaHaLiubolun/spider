package com.chinamcloud.spider.pipeline;

import us.codecraft.webmagic.Task;

import java.util.Map;


public class ConsolePipeLine implements ObjectPipeline {

    @Override
    public void process(Object o, Task task) {
        Map<String, Object> map = (Map) o;
        for (Map.Entry<String, Object> m : map.entrySet()) {
            System.out.println(m.getKey() + ":  " + m.getValue());
        }
    }
}
