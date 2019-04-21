package com.chinamcloud.spider.extend.process;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class ExtentCProcess implements PageProcessor {


    @Override
    public void process(Page page) {
        Class c = (Class) page.getRequest().getExtra("");

    }

    @Override
    public Site getSite() {
        return null;
    }
}
