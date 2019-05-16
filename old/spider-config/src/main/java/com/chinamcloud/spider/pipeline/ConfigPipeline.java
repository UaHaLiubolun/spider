package com.chinamcloud.spider.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.Vector;


public class ConfigPipeline implements Pipeline {


    private List<ObjectPipeline> objectPipelines = new Vector<>();

    public ConfigPipeline put(ObjectPipeline objectPipeline) {
        objectPipelines.add(objectPipeline);
        return this;
    }


    @Override
    public void process(ResultItems resultItems, Task task) {
        if (objectPipelines.size() == 0) objectPipelines.add(new ConsolePipeLine());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            objectPipelines.stream().forEach(objectPipeline -> {
                try {
                    objectPipeline.process(entry.getValue(), task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
