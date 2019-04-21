package com.chinamcloud.spider.any.proxy.pojo;

import java.util.HashMap;
import java.util.Map;

public class RequestUrl {

    private String url;

    private String paramS;

    private Map<String, String> param = new HashMap<>();

    public RequestUrl(String url) {
        init(url);
    }


    private void init(String url) {
        String[] urlS = url.split("\\?");
        if (urlS.length  != 2) return;
        this.url = urlS[0];
        paramS = urlS[1];
        parseParam();
    }

    private void parseParam() {
        if (paramS == null || paramS.equals("")) return;
        String[] params = paramS.split("&");
        for (String p:
             params) {
            int index = p.indexOf("=");
            if (p.length() == (index + 1)) {
                param.put(p.substring(0, index), "");
            } else {
                param.put(p.substring(0, index), p.substring(index + 1));
            }
        }
    }

    public String getParam(String key) {
        return param.get(key);
    }

    public void setParam(String key, String value) {
        param.put(key, value);
    }

    public void delete(String key) {
        param.remove(key);
    }

    public String toUrl() {
        String s = "";
        for (Map.Entry<String, String> entry : param.entrySet()) {
            s = s +  entry.getKey() + "=" + entry.getValue() + "&";
        }
        s = s.substring(0, s.length() - 1);
        return url + "?" + s;
    }

    public static void main(String[] args) {
        String u = "https://mp.weixin.qq.com/mp/profile_ext?action=getmsg&__biz=MjM5MjA0MDk2MA==&f=json&offset=0&count=10&is_ok=1&scene=126&uin=777&key=777&pass_ticket=%2ByVXAC92iqqk3pbJJZGlEDYL0z%2B8swLY0Bg3yPj5qhozcSbMWyKq7TFuvqxhHvGk&wxtoken=&appmsg_token=991_mV7oa6sn5svjJ5LKCuKsc6feP1zoGXB9FWElhQ~~&x5=0&f=json";
        RequestUrl url = new RequestUrl(u);
        System.out.println(url.toUrl());
    }
}
