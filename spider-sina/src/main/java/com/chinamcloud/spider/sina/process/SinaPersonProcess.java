package com.chinamcloud.spider.sina.process;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Json;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SinaPersonProcess implements ISinaProcess{

    @Override
    public void process(Page page) {
        Document htmlSec = Jsoup.parse(page.getRawText());

        Elements elements = htmlSec.getElementsByTag("script");
        Document document = getScript(elements, "\"ns\":\"\",\"domid\":\"Pl_Core_T8CustomTriColumn__53\"");
        Document info = getScript(elements, "\"ns\":\"\",\"domid\":\"Pl_Official_PersonalInfo__57\"");
        Document levelDoc = getScript(elements, "\"ns\":\"\",\"domid\":\"Pl_Official_RightGrowNew__55\"");

        Map<String, String> result = new HashMap<>();
        Elements elements1 = document.getElementsByTag("td");
        result.put("follow_count", elements1.get(0).getElementsByTag("strong").get(0).text());
        result.put("followers_count", elements1.get(1).getElementsByTag("strong").get(0).text());
        result.put("statuses_count", elements1.get(2).getElementsByTag("strong").get(0).text());
        String level = levelDoc
                .getElementsByClass("level_info").get(0)
                .getElementsByTag("span").get(0)
                .getElementsByTag("span").get(0).text();
        result.put("level", level);

        Elements li = info.getElementsByAttributeValue("class", "li_1 clearfix");
        for (Element element : li) {
//            if (element.getElementsByTag("span").get(0).getElementsByTag("em").get(0).text().equals("2")) {
//                String place = element.getElementsByTag("span").get(1).text();
//                result.put("place", place);
//                break;
//            }
            Elements elements2 = element.getElementsByTag("span");
            if (elements2.size() == 1) {
                Elements ela = element.getElementsByTag("a");
                result.put(elements2.get(0).text(), ela.get(0).text());
            } else {
                result.put(elements2.get(0).text(), elements2.get(1).text());
            }
        }

        result.put("personId", page.getRequest().getExtra("personId").toString());
        result.put("domain", page.getRequest().getExtra("domain").toString());
        page.getResultItems().put("type", "person");
        page.getResultItems().put("person", result);
    }

    private Document getScript(Elements elements, String key) {
        for (Element element : elements) {
            String s = element.data();
            if (s.contains(key)) {
                Pattern pattern = Pattern.compile("FM.view\\(\\{(.*?)}\\)");
                Matcher matcher = pattern.matcher(s);
                String json = null;
                if (matcher.find()) {
                    json = matcher.group(1);
                }
                if (json == null) return null;
                json = "{" + json + "}";
                Json jsonSe = new Json(json);
                String html = jsonSe.jsonPath("$.html").get();
                Document document = Jsoup.parse(html);
                return document;
            }
        }
        return null;
    }

    private Document getScript(Elements elements, int size) {
        String s = elements.get(elements.size() - size).data();
        Pattern pattern = Pattern.compile("FM.view\\((.*?)\\)");
        Matcher matcher = pattern.matcher(s);
        String json = null;
        if (matcher.find()) {
            json = matcher.group(1);
        }
        if (json == null) return null;
        Json jsonSe = new Json(json);
        String html = jsonSe.jsonPath("$.html").get();
        Document document = Jsoup.parse(html);
        return document;
    }
}
