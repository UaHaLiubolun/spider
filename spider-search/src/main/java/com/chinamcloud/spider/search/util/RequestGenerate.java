package com.chinamcloud.spider.search.util;

import us.codecraft.webmagic.Request;

import java.util.Map;

public class RequestGenerate {

    private static String baiduUrl = "http://news.baidu.com/ns?word={kw}&cl=2&ct=1&tn=news&rn=20&ie=utf-8&bt=0&et=0";

    private static String sougouUrl = "https://news.sogou.com/news?query={kw}";

    private static String[] userAgents = {"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36"};

    public static Request baidu(String kw) {
        String url = baiduUrl.replace("{kw}", kw);
        Request request = new Request(url);
        request.putExtra("type", SearchConst.BAIDU_TYPE);
        request.putExtra("sourceId", Math.abs(SearchConst.TB_BAIDU.hashCode()));
        request.putExtra("tbNickName", SearchConst.TB_BAIDU);
        request.setCharset("utf-8");
        setUserAgent(request);
        return request;
    }

    public static Request site(Site site, String kw) {
        String url = site.getUrl().replace("{kw}", kw);
        Request request = new Request(url);
        request.putExtra("type", site.getType());
        request.putExtra("sourceId", site.getSourceId());
        request.putExtra("tbNickName", site.getTbNickName());
        request.setCharset(site.getCharset());
        setUserAgent(request);
        return request;
    }

    public static Request content(Map<String, Object> map, Request request) {
        Request request1 = new Request(map.get("url").toString());
        request1.putExtra("type", SearchConst.CONTENT_TYPE);
        request1.putExtra("parentSource", map.get("source"));
        request1.putExtra("pubTime", map.get("time"));
        request1.putExtra("subject", map.get("title"));
        request1.setExtractLinks(false);
        request1.setCharset(null);
        request1.setBinaryContent(false);
        request1.putExtra("tbNickName", request.getExtra("tbNickName"));
        request1.putExtra("sourceId", request.getExtra("sourceId"));
        return request1;
    }

    private static void setUserAgent(Request request) {
        String s = userAgents[0];
        request.addHeader("User-Agent", s);
    }
}
