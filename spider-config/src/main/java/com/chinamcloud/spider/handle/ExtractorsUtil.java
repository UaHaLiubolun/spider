package com.chinamcloud.spider.handle;

import com.chinamcloud.spider.model.Extract;
import us.codecraft.webmagic.selector.*;

import java.util.ArrayList;
import java.util.List;


public class ExtractorsUtil {

    public static List<Selector> getSelector(Extract extract) {
        List<Selector> selectors = new ArrayList<>();
        extract.getValue().stream().forEach(
                value -> selectors.add(chooseSelector(extract.getType(), value))
        );
        return selectors;
    }

    private static Selector chooseSelector(Extract.Type type, String value) {
        Selector selector;
        switch (type) {
            case Css:
                selector = new CssSelector(value);
                break;
            case Regex:
                selector = new RegexSelector(value);
                break;
            case XPath:
                selector = new XpathSelector(value);
                break;
            case JsonPath:
                selector = new JsonPathSelector(value);
                break;
            default:
                selector = new XpathSelector(value);
        }
        return selector;
    }
}
