package cn.stark.spider.download;

import okhttp3.Request;

public final class RequestBuilder {

    private RequestBuilder() {
    }

    public static Request build(cn.stark.spider.common.Request request) {
        Request r = new Request.Builder().url(request.getUrl())
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
                .header("Referer", "https://movie.douban.com/tag/")
                .build();
        return r;
    }
}
