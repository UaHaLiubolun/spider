package cn.stark.spider.download;

import okhttp3.Request;

public final class RequestBuilder {

    private RequestBuilder() {
    }

    public static Request build(cn.stark.spider.common.Request request) {
        Request r = new Request.Builder().url(request.getUrl()).build();
        return r;
    }
}
