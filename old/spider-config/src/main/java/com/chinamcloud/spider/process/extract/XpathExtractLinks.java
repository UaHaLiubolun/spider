package com.chinamcloud.spider.process.extract;

import com.chinamcloud.spider.model.PageModel;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selector;
import us.codecraft.webmagic.selector.XpathSelector;

import java.util.List;

public class XpathExtractLinks extends ExtractLinks {

    public XpathExtractLinks(Page page, String expression, PageModel pageModel, boolean isExtractLinks) {
        this.expression = expression;
        this.page = page;
        this.pageModel = pageModel;
        this.isExtractLinks = isExtractLinks;
    }

    public XpathExtractLinks() {

    }

    @Override
    public List<String> extractUrl(Page page, String expression) {
        if (expression == null) return null;
        Selector selector = new XpathSelector(expression);
        List<String> urls = page.getHtml().select(selector).all();
        return urls;
    }
}
