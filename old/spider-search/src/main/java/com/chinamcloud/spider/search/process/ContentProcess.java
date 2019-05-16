package com.chinamcloud.spider.search.process;

import com.chinamcloud.spider.search.util.XPathUtil;
import com.chinamcloud.spider.search.webCollector.ContentExtractor;
import com.chinamcloud.spider.search.webCollector.News;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;

import java.util.*;

public class ContentProcess implements ISearchProcess {

    @Override
    public void process(Page page) {
        try {
            News news = ContentExtractor.getNewsByDoc(page.getHtml().getDocument());
            Element element = news.getContentElement();
            Map<String, Object> map = new HashMap<>();
            map.put("description", XPathUtil.newsText(element));
            map.put("pics", XPathUtil.imgs(element));
            map.put("parentSource", page.getRequest().getExtra("parentSource"));
            map.put("pubTime", page.getRequest().getExtra("pubTime"));
            map.put("subject", page.getRequest().getExtra("subject"));
            map.put("refererUrl", page.getRequest().getUrl());
            map.put("langType", "ch");
            map.put("source", "Search");
            map.put("createdAt", new Date().getTime());
            map.put("sourceId", page.getRequest().getExtra("sourceId"));
            map.put("isCralwer", 1);
            map.put("tbNickName", page.getRequest().getExtra("tbNickName"));
            page.getResultItems().put("content", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
