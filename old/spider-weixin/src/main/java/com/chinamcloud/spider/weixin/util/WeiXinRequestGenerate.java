package com.chinamcloud.spider.weixin.util;

import us.codecraft.webmagic.Request;

public class WeiXinRequestGenerate {

    public static String gzhSearch = "http://weixin.sogou.com/weixin?type=1&s_from=input&ie=utf8&_sug_=n&_sug_type_=&query=";

    public static String keywordSearch = "https://weixin.sogou.com/weixin?type=2&ie=utf8&tsn=1&ft=&et=&interation=&wxid=&usip=&query=";

    public static Request gzhUrl(String url, String gzhId, String classification, String name){
        Request request = new Request();
        request.setUrl(url);
        request.putExtra("gzhId", gzhId);
        request.putExtra("gzhName", name);
        request.putExtra("ProcessType", "list");
        request.putExtra("UnlockType", "weixin");
        request.putExtra("classification", classification);
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        return request;
    }

    public static Request contentRequest(String url, String gzhId, String classification, String name){
        Request request = new Request();
        request.setUrl(url);
        request.setExtractLinks(false);
        request.putExtra("gzhId", gzhId);
        request.putExtra("ProcessType", "content");
        request.putExtra("UnlockType", "weixin");
        request.putExtra("classification", classification);
        request.putExtra("gzhName", name);
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        return request;
    }

    public static Request searchRequest(String gzhId, String classification, String name){
        Request request = new Request();
        request.setUrl(gzhSearch + gzhId);
        request.putExtra("gzhId", gzhId);
        request.putExtra("ProcessType", "search");
        request.putExtra("UnlockType", "sogou");
        if (classification == null || classification.equals("")) {
            classification = "未分类";
        }
        request.putExtra("gzhName", name);
        request.putExtra("classification", classification);
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        return request;
    }

    public static Request kwRequest(String kw) {
        Request request = new Request(keywordSearch + kw);
        request.putExtra("kw", kw);
        request.putExtra("ProcessType", "kw");
        request.putExtra("UnlockType", "sogou");
        request.addHeader("Referer", gzhSearch + kw);
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        return request;
    }
}
