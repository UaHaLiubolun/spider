package com.chinamcloud.spider.any.proxy.process;

import com.chinamcloud.spider.weixin.process.ContentProcess;
import com.chinamcloud.spider.weixin.process.IWeiXinProcess;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import static com.chinamcloud.spider.any.proxy._const.AnyProxyConst.*;

public class AnyProxyProcess implements PageProcessor {

    private Site site;

    public AnyProxyProcess(Site site) {
        this.site = site;
    }


    @Override
    public void process(Page page) {
        String queueType = page.getRequest().getExtra(URL_TYPE).toString();
        IWeiXinProcess weiXinProcess = switchProcess(queueType);
        if (weiXinProcess == null) return;
        weiXinProcess.process(page);
    }


    private IWeiXinProcess switchProcess(String queueType) {
        if (queueType.equals(LOAD_MORE)) {
            return new LoadMoreProcess();
        } else if (queueType.equals(CONTENT)) {
            return new ContentProcess();
        }
        return null;
    }

    @Override
    public Site getSite() {
        return this.site;
    }
}
