package com.chinamcloud.spider.process.extract;

import com.chinamcloud.spider.model.PageModel;
import us.codecraft.webmagic.Page;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexAllExtractLinks extends ExtractLinks {

    public RegexAllExtractLinks(Page page, String expression, PageModel pageModel, boolean isExtractLinks) {
        this.expression = expression;
        this.page = page;
        this.pageModel = pageModel;
        this.isExtractLinks = isExtractLinks;
    }

    public RegexAllExtractLinks() {
    }

    @Override
    public List<String> extractUrl(Page page, String expression) {
        List<String> stringList = new LinkedList<>();
        if (expression == null) return stringList;
        expression = "(" + expression + ")";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(page.getRawText());
        while (matcher.find()) {
            String url = matcher.group(0);
            stringList.add(url);
        }
        return stringList;
    }
}
