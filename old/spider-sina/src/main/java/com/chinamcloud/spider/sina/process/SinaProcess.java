package com.chinamcloud.spider.sina.process;

import com.chinamcloud.spider.sina.utils.WeiBoConst;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class SinaProcess implements PageProcessor {

    private Site site = Site.me().setDomain("weibo.cn").setSleepTime(0);


    @Override
    public void process(Page page) {
        ISinaProcess sinaProcess = new SinaDetailProcess();
        switch (page.getRequest().getExtra("ProcessType").toString()) {
            case "detail":
                sinaProcess = new SinaDetailProcess();
                break;
            case "review":
                sinaProcess = new SinaHotFlowProcess();
                break;
            case "webReview":
                sinaProcess = new SinaCommentBigProcess();
                break;
            case "webRePost":
                sinaProcess = new SinaMBlogBigProcess();
                break;
            case "home":
                sinaProcess = new SinaHomeProcess();
                break;
            case "person":
                sinaProcess = new SinaPersonProcess();
                break;
        }
        if (page.getRequest().getExtra(WeiBoConst.PROCESS_TYPE).equals(WeiBoConst.PERSION_PROFILE)) {
            sinaProcess = new SinaPersionProfileProcess();
        }
        sinaProcess.process(page);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
