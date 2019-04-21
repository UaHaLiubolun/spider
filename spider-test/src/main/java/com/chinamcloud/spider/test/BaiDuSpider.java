package com.chinamcloud.spider.test;

import com.chinamcloud.spider.test.webCollector.ContentExtractor;
import com.chinamcloud.spider.test.webCollector.News;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaiDuSpider {

    private static HttpClientDownloader downloader;

    private static String baiduNewsUrl =
            "http://news.baidu.com/ns?word={kw}&pn={offset}&cl=2&ct=1&tn=news&rn={pageNum}&ie=utf-8&bt=0&et=0";

    private static String URL2ioUrl = "http://api.url2io.com/article?token=EGoQHzKoQYm5ywKsS_V2oQ&url=";


    static {
        downloader = new HttpClientDownloader();
    }

    public List<Map<String, Object>> spider(String kw) {
        List<Map<String, Object>> list = newsList(kw);
        for (Map<String, Object> m:
             list) {
            detail(m);
        }
        return list;
    }

    public List<Map<String, Object>> newsList(
            String kw,
            String offset,
            String pageNum
    ) {
        Request request = new Request(tranUrl(kw, offset, pageNum));
        request.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        Page page = downloader.download(request, Site.me().setCharset("utf-8").toTask());
        List<Map<String, Object>> result = getList(page.getHtml().getDocument());
        return result;
    }

    public List<Map<String, Object>> newsList(String kw) {
        return newsList(kw, "0", "20");
    }

    private List<Map<String, Object>> getList(Document document) {
        Elements newsList = document.getElementsByClass("result");
        if (newsList == null || newsList.size() == 0)  return null;
        List<Map<String, Object>> result = new ArrayList<>(newsList.size());
        for (Element node:
                newsList) {
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
            map.put("time", String.valueOf(tranTime(source)));
            map.put("source", source.replaceFirst("\\d+小时前", "")
                    .replaceFirst("\\d+分钟前", "")
                    .replaceFirst("\\d+年\\d+月\\d+日 \\d+:\\d+", "").replace(" ", ""));
            result.add(map);
        }

        return result;
    }

    private void detail(Map<String, Object> map) {
        Html html = downloader.download(map.get("url").toString());
        try {
            News news = ContentExtractor.getNewsByDoc(html.getDocument());
            Element element = news.getContentElement();
            map.put("description", newsText(element));
            Elements elements = element.getElementsByTag("img");
            if (elements.size() != 0) {
                List<Object> stringList = new ArrayList<>(elements.size());
                Iterator<Element> iterator = elements.iterator();
                while (iterator.hasNext()) {
                    stringList.add(iterator.next().attr("src"));
                }
                map.put("pics", stringList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String newsText(Element element) {
        return element.html().
                replaceAll("<script[^>]*?>[\\s\\S]*?</script>", "").
                replaceAll("<style[^>]*?>[\\s\\S]*?</style>", "").
                replaceAll("<img(\\S*?)[^>]*>.*?|<.*? />", "[[+_+]]").
                replaceAll("<p(\\S*?)[^>]*>.*?|<.*? />", "`n`").
                replaceAll("<(\\S*?)[^>]*>.*?|<.*? />", "").
                replaceAll("\n|\r", "");
    }

    private long tranTime(String timeStr) {
        if (timeStr.contains("小时前")) {
            long curTime = new Date().getTime();
            String regex = "(\\d+)小时前";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(timeStr);
            if (matcher.find()) {
                int hours = Integer.parseInt(matcher.group(1));
                return (curTime - (hours * 60 * 60 * 1000));
            }
        } else if (timeStr.contains("分钟前")) {
            long curTime = new Date().getTime();
            String regex = "(\\d+)分钟前";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(timeStr);
            if (matcher.find()) {
                int hours = Integer.parseInt(matcher.group(1));
                return (curTime - (hours * 60 * 1000));
            }
        } else {
            String regex = "(\\d+年\\d+月\\d+日 \\d+:\\d+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(timeStr);
            if (matcher.find()) {
                timeStr = matcher.group(1);
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            try {
                return simpleDateFormat.parse(timeStr).getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    private String tranUrl(String kw, String offset, String pageNum) {
        return baiduNewsUrl.replace("{kw}", kw)
                .replace("{offset}", offset)
                .replace("{pageNum}", pageNum);
    }

}
