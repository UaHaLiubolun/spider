package com.chinamcloud.spider.search.util;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XPathUtil {

    public static String newsText(Element element) {
        return element.html().
                replaceAll("<script[^>]*?>[\\s\\S]*?</script>", "").
                replaceAll("<style[^>]*?>[\\s\\S]*?</style>", "").
                replaceAll("<img(\\S*?)[^>]*>.*?|<.*? />", "[[+_+]]").
                replaceAll("<p(\\S*?)[^>]*>.*?|<.*? />", "`n`").
                replaceAll("<(\\S*?)[^>]*>.*?|<.*? />", "").
                replaceAll("\n|\r", "");
    }

    public static List<String> imgs(Element element) {
        Elements elements = element.getElementsByTag("img");
        if (elements.size() != 0) {
            List<String> stringList = new ArrayList<>(elements.size());
            Iterator<Element> iterator = elements.iterator();
            while (iterator.hasNext()) {
                stringList.add(iterator.next().attr("src"));
            }
            return stringList;
        }
        return new ArrayList<>();
    }
}
