package com.chinamcloud.spider.any.proxy.pojo;

import us.codecraft.webmagic.Request;

import java.util.Map;

public class RequestParser {

    public static Request parser(WeChatRequest weChatRequest) {
        Request request = new Request(weChatRequest.getUrl());
        request.setMethod(weChatRequest.getRequestOptions().getMethod());

        for (Map.Entry<String, String> entry:
             weChatRequest.getRequestOptions().getHeaders().entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }

        String cookie = weChatRequest.getRequestOptions().getHeaders().get("Cookie");
        if (cookie != null) {
            String[] cookies = cookie.split(";");
            for (String c:
                 cookies) {
                String[] temp = c.split("=");
                if (temp.length != 2) continue;
                request.addCookie(temp[0], temp[1]);
            }
        }
        return request;
    }
}
