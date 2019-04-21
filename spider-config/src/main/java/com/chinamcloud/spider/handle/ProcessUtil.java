package com.chinamcloud.spider.handle;

import us.codecraft.webmagic.Page;

import java.util.Map;

public class ProcessUtil {

    public static void tranToMap(Map<String, Object> map, Page page) {
        map.put("refererUrl", page.getRequest().getUrl());
        map.put("sourceUrl", page.getRequest().getSourceUrl());
        map.put("source", page.getRequest().getExtra("source"));
        map.put("classification", page.getRequest().getExtra("type"));
        map.put("tbNickName", page.getRequest().getExtra("sourceName"));
    }
}
