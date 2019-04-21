package com.chinamcloud.spider.process.extract;

import com.chinamcloud.spider.model.PageModel;
import us.codecraft.webmagic.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExtractLinks extends ExtractLinks {

    public RegexExtractLinks(Page page, String expression, PageModel pageModel, boolean isExtractLinks) {
        this.expression = expression;
        this.page = page;
        this.pageModel = pageModel;
        this.isExtractLinks = isExtractLinks;
    }

    public RegexExtractLinks() {
    }

    @Override
    public List<String> extractUrl(Page page, String expression) {
        List<String> list = new ArrayList<>();
        if (expression == null) return list;
        List<String> links = page.getHtml().links().all();
        links.stream().forEach(link -> {
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(link);
            if (matcher.find()) {
                String url = matcher.group(0);
                list.add(url);
            }
        });
        return list;
    }
}
