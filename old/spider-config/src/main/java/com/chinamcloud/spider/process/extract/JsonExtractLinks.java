package com.chinamcloud.spider.process.extract;

import com.chinamcloud.spider.model.PageModel;
import us.codecraft.webmagic.Page;

import java.util.ArrayList;
import java.util.List;

public class JsonExtractLinks extends ExtractLinks {

    public JsonExtractLinks(Page page, String expression, PageModel pageModel, boolean isExtractLinks) {
        this.expression = expression;
        this.page = page;
        this.pageModel = pageModel;
        this.isExtractLinks = isExtractLinks;
    }

    public JsonExtractLinks() {
    }

    @Override
    public List<String> extractUrl(Page page, String expression) {
        if (expression == null) return null;
        List<String> links = new ArrayList<>();
        links.addAll(page.getJson().jsonPath(expression).all());
        return links;
    }
}
