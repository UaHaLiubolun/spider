package com.chinamcloud.spider;

import com.chinamcloud.spider.pipeline.ConfigPipeline;
import com.chinamcloud.spider.pipeline.ConsolePipeLine;
import com.chinamcloud.spider.pipeline.ObjectPipeline;
import com.chinamcloud.spider.process.ConfigTwoPageProcess;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class ConfigTwoSpider extends Spider {


    private ConfigPipeline configPipeline;

    private ConfigTwoPageProcess configTwoPageProcess;

    public ConfigTwoSpider(PageProcessor pageProcessor) {
        super(pageProcessor);
    }


    public ConfigTwoSpider(ConfigTwoPageProcess configTwoPageProcess) {
        super(configTwoPageProcess);
        this.configTwoPageProcess = configTwoPageProcess;
    }

    public void putConfigPipeline(ObjectPipeline pipeline) {
        this.configPipeline.put(pipeline);
    }


    private ConfigTwoSpider(Site site, ObjectPipeline objectPipeline) {
        super(new ConfigTwoPageProcess(site));
        this.configPipeline = new ConfigPipeline();
        configPipeline.put(objectPipeline);
        super.addPipeline(configPipeline);
    }

    public static ConfigTwoSpider create(Site site) {
        return new ConfigTwoSpider(site, new ConsolePipeLine());
    }

    public static ConfigTwoSpider create(Site site, ObjectPipeline objectPipeline) {
        return new ConfigTwoSpider(site, objectPipeline);
    }
}
