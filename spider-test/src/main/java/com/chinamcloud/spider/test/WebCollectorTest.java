package com.chinamcloud.spider.test;

import com.chinamcloud.spider.orm.dao.MapDao;
import com.chinamcloud.spider.test.webCollector.ContentExtractor;
import com.chinamcloud.spider.test.webCollector.News;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebCollectorTest {

    public static void main(String[] args) {

        MapDao mapDao = new MapDao("spider_news");
        List<Map> maps = mapDao.getAll();
        List<Map> notNull = maps.stream().filter(map -> map.get("description") != null).collect(Collectors.toList());
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        MapDao mapDao1 = new MapDao("spider_news_common");
        notNull.stream().forEach(map -> {
            String url = map.get("refererUrl").toString();
            String charset = "utf-8";
            if (url.contains("people.com")) {
                charset = "gbk";
            }
            Html html = httpClientDownloader.download(url, charset);
            try {
                News news = ContentExtractor.getNewsByDoc(html.getDocument());
                Map<String, String> map1 = new HashMap<>();
                map1.put("refererUrl", url);
                map1.put("subject", news.getTitle());
                map1.put("pubTime", news.getTime());
                String content = news.getContentElement().html().
                        replaceAll("<script[^>]*?>[\\s\\S]*?</script>", "").
                        replaceAll("<style[^>]*?>[\\s\\S]*?</style>", "").
                        replaceAll("<img(\\S*?)[^>]*>.*?|<.*? />", "[[+_+]]").
                        replaceAll("<p(\\S*?)[^>]*>.*?|<.*? />", "`n`").
                        replaceAll("<(\\S*?)[^>]*>.*?|<.*? />", "").
                        replaceAll("\n|\r", "");
                map1.put("description", content);
                mapDao1.add(map1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
