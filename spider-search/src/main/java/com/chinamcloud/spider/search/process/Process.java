package com.chinamcloud.spider.search.process;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class Process implements PageProcessor {

    private Site site;

    public Process(Site site) {
        this.site = site;
    }

    @Override
    public void process(Page page) {
        ISearchProcess process = ProcessFactory.getInstance(page.getRequest().getExtra("type").toString());
        if (process == null) return;
        process.process(page);
    }

    @Override
    public Site getSite() {
        return this.site;
    }
}
