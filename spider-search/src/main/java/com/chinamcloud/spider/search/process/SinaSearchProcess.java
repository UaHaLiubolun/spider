package com.chinamcloud.spider.search.process;

import com.chinamcloud.spider.search.util.RequestGenerate;
import com.chinamcloud.spider.search.util.TimeUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import java.util.HashMap;
import java.util.Map;

public class SinaSearchProcess implements ISearchProcess {


    // TODO 乱码问题
    @Override
    public void process(Page page) {
        Elements newsList = page.getHtml().getDocument().getElementsByClass("box-result");
        if (newsList == null || newsList.size() == 0)  return;
        for (Element node:
                newsList) {
            try {
                Map<String, Object> map = new HashMap<>();
                Element titleEl =  node.getElementsByTag("h2").get(0);
                String url = titleEl.getElementsByTag("a").get(0).attributes().get("href");
                String title = titleEl.text();
                String source = titleEl.getElementsByClass("fgray_time").text();
                Element desEle = node.getElementsByClass( "content").get(0);
                String des = desEle.text();
                map.put("url", url);
                map.put("title", title);
                map.put("des", des);
                map.put("time", String.valueOf(TimeUtil.tranTime(source, "(\\d+-\\d+-\\d+ \\d+:\\d+:\\d+)", "yyyy-MM-dd HH:mm:ss")));
                map.put("source", source.replaceFirst("\\d+小时前", "")
                        .replaceFirst("\\d+分钟前", "")
                        .replaceFirst("\\d+-\\d+-\\d+ \\d+:\\d+:\\d+", "").replace(" ", ""));
                Request request = RequestGenerate.content(map, page.getRequest());
                page.addTargetRequest(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
