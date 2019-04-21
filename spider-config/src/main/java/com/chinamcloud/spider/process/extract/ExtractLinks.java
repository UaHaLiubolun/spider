package com.chinamcloud.spider.process.extract;

import com.chinamcloud.spider.PageModelTran;
import com.chinamcloud.spider.handle.UrlsUtil;
import com.chinamcloud.spider.model.PageModel;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import java.util.List;

public abstract class ExtractLinks {

    protected Page page;

    protected PageModel pageModel;

    protected boolean isExtractLinks;

    protected String expression;

    public abstract List<String> extractUrl(Page page, String expression);

    public void extract() {
        List<String> list = extractUrl(page, expression);
        if (list == null || list.size() == 0) return;
        list.stream().forEach(l -> page.addTargetRequest(generateRequest(page, l, pageModel, isExtractLinks)));
    }

    private Request generateRequest(Page page, String url, PageModel pageModel, Boolean isExtractLinks) {
        Request request;
        if (isExtractLinks == null) {
            request = new Request(url, UrlsUtil.isExtractLinks(url, pageModel.getTargetUrl()));
        } else {
            request = new Request(url, isExtractLinks);
        }
        request.setSourceUrl(page.getRequest().getUrl());
        request.putExtra("PageModel", PageModelTran.getPageModel(page));
        request.setInterval(page.getRequest().getInterval());
        request.putExtra("source", page.getRequest().getExtra("source"));
        request.putExtra("type", page.getRequest().getExtra("type"));
        request.putExtra("sourceName", page.getRequest().getExtra("sourceName"));
        request.setCharset(page.getRequest().getCharset());
        request.setTest(page.getRequest().isTest());
        return request;
    }

}
