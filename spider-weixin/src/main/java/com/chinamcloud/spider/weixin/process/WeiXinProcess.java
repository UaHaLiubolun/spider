package com.chinamcloud.spider.weixin.process;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


public class WeiXinProcess implements PageProcessor {

    private Site site;

    public WeiXinProcess(Site site) {
        this.site = site;
    }

    @Override
    public void process(Page page) {
        IWeiXinProcess weiXinProcess = new ContentProcess();
        switch (page.getRequest().getExtra("ProcessType").toString()) {
            case "search":
                weiXinProcess = new SearchProcess();
                break;
            case "list":
                weiXinProcess = new ListProcess();
                break;
            case "kw":
                weiXinProcess = new KwProcess();
                break;
        }
        weiXinProcess.process(page);
    }

    @Override
    public Site getSite() {
        return this.site;
    }


}
