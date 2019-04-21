package com.chinamcloud.spider.search.process;

import com.chinamcloud.spider.search.util.RequestGenerate;
import com.chinamcloud.spider.search.util.TimeUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SoGouSearchProcess implements ISearchProcess {

    @Override
    public void process(Page page) {
        Elements newsList = page.getHtml().getDocument().getElementsByClass("vrwrap");
        if (newsList == null || newsList.size() == 0)  return;
        for (Element node:
                newsList) {
            try {
                Map<String, Object> map = new HashMap<>();
                Element titleEl =  node.getElementsByTag("h3").get(0);
                String url = titleEl.getElementsByTag("a").get(0).attributes().get("href");
                String title = titleEl.text();
                String source = node.getElementsByClass("news-from").text();
                Element desEle = node.getElementsByClass( "news-txt").get(0);
                String des = desEle.text();
                map.put("url", url);
                map.put("title", title);
                map.put("des", des);
                map.put("time", String.valueOf(TimeUtil.tranTime(source, "(\\d+-\\d+-\\d+)", "yyyy-MM-dd")));
                map.put("source", source.replaceFirst("\\d+小时前", "")
                        .replaceFirst("\\d+分钟前", "")
                        .replaceFirst("\\d+-\\d+-\\d+", "").replace(" ", ""));
                Request request = RequestGenerate.content(map, page.getRequest());
                page.addTargetRequest(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
