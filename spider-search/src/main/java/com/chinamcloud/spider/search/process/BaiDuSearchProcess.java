package com.chinamcloud.spider.search.process;

import com.chinamcloud.spider.search.util.RequestGenerate;
import com.chinamcloud.spider.search.util.TimeUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaiDuSearchProcess implements ISearchProcess {


    @Override
    public void process(Page page) {
        Elements newsList = page.getHtml().getDocument().getElementsByClass("result");
        if (newsList == null || newsList.size() == 0)  return;
        for (Element node:
                newsList) {
            try {
                Map<String, Object> map = new HashMap<>();
                Element titleEl =  node.getElementsByTag("h3").get(0);
                String url = titleEl.getElementsByTag("a").get(0).attributes().get("href");
                String title = titleEl.text();
                String source = node.getElementsByClass("c-author").text();
                Element desEle = node.getElementsByClass( "c-summary").get(0);
                String span =  desEle.getElementsByTag("span").text();
                String des = desEle.text();
                des = des.replace(span, "").replace(source, "");
                map.put("url", url);
                map.put("title", title);
                map.put("des", des);
                map.put("time", String.valueOf(TimeUtil.tranTime(source, "(\\d+年\\d+月\\d+日 \\d+:\\d+)", "yyyy年MM月dd日 HH:mm")));
                map.put("source", source.replaceFirst("\\d+小时前", "")
                        .replaceFirst("\\d+分钟前", "")
                        .replaceFirst("\\d+年\\d+月\\d+日 \\d+:\\d+", "").replace(" ", ""));
                Request request = RequestGenerate.content(map, page.getRequest());
                page.addTargetRequest(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
