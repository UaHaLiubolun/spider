package com.chinamcloud.spider.any.proxy.util;

import us.codecraft.webmagic.Request;
import static com.chinamcloud.spider.any.proxy._const.AnyProxyConst.*;

public class RequestGenerate {



    public static Request content(String url, String biz, int date) {
        Request request = new Request(url);
        request.putExtra(URL_TYPE, CONTENT);
        request.putExtra(BIZ, biz);
        request.putExtra("date", String.valueOf(date));
        request.setExtractLinks(false);
        return request;
    }
}
