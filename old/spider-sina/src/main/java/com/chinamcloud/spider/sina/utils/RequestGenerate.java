package com.chinamcloud.spider.sina.utils;

import com.chinamcloud.spider.sina.cookie.CookieUtil;
import us.codecraft.webmagic.Request;

public class RequestGenerate {


    private static String rePostUrl = "https://weibo.com/aj/v6/mblog/info/big?ajwvr=6&id=";

    private static String commentUrl = "https://weibo.com/aj/v6/comment/big?ajwvr=6&id=";

    private static String homeUrl = "https://weibo.com/";

    private static String personUrl = "https://weibo.com/p/{}/info?mod=pedit_more";

    private static String mWeiBoUrl = "https://m.weibo.cn/detail/";

    private static String mWeiProfile = "https://m.weibo.cn/profile/info?uid=";

    public static Request rePost(String weiboId) {
        return rePost(weiboId, weiboId, weiboId, 1, 1);
    }


    public static Request mWeiProfile(String userId) {
        Request request = new Request(mWeiProfile + userId);
        request.putExtra(WeiBoConst.PROCESS_TYPE, WeiBoConst.PERSION_PROFILE);
        request.putExtra("UserId", userId);
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        return request;
    }

    public static Request mWeiBo(String weiBoId) {
        Request request = new Request();
        request.setUrl(mWeiBoUrl + weiBoId);
        request.setCookies(CookieUtil.getCookie());
        request.putExtra("weiboId", weiBoId);
        request.putExtra("ProcessType", "detail");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        return request;
    }

    public static Request rePost(String weiboId, String parentId, String topId, int page, int rePostNum) {
        Request request = new Request();
        request.setUrl(rePostUrl + weiboId +  "&page=" + page);
        request.setCookies(CookieUtil.getCookie());
        request.putExtra("weiboId", weiboId);
        request.putExtra("ProcessType", "webRePost");
        request.putExtra("parentId", parentId);
        request.putExtra("topId", topId);
        request.putExtra("rePostNum", (rePostNum));
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        return request;
    }

    public static Request comment(String weiboId, int page) {
        Request request = new Request();
        request.setUrl(commentUrl + weiboId +  "&page=" + page);
        request.setCookies(CookieUtil.getCookie());
        request.putExtra("weiboId", weiboId);
        request.putExtra("ProcessType", "webReview");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        return request;
    }

    public static Request home(String nicknameId, String personId) {
        Request request = new Request();
        request.setUrl(homeUrl + nicknameId);
        request.setCookies(CookieUtil.getCookie());
        request.putExtra("personId", personId);
        request.putExtra("ProcessType", "home");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        return request;
    }

    public static Request person(String domain, String personId) {
        Request request = new Request();
        request.setUrl(personUrl.replace("{}", domain + personId));
        request.setCookies(CookieUtil.getCookie());
        request.putExtra("personId", personId);
        request.putExtra("domain", domain);
        request.putExtra("ProcessType", "person");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
        return request;
    }
}
