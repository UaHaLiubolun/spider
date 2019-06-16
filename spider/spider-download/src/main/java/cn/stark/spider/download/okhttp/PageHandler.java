package cn.stark.spider.download.okhttp;

import cn.stark.spider.common.Page;
import cn.stark.spider.common.Request;
import cn.stark.spider.common.selector.PlainText;
import okhttp3.Response;

import java.io.IOException;

public class PageHandler {

    public static Page handleResponse(Request request, Response response) throws IOException {
        byte[] bytes = response.body().bytes();
//        String contentType = response.body().contentType().type();
        Page page = new Page();
        page.setBytes(bytes);
        if (!request.isBinaryContent()) {
            String charset;
            if (request.getCharset() == null) {
                charset = response.body().contentType().charset().displayName();
            } else {
                charset = request.getCharset();
            }
            page.setCharset(charset);
        }
        page.setRequest(request);
        page.setUrl(new PlainText(request.getUrl()));
        page.setStatusCode(response.code());
        page.setDownloadSuccess(true);
        return page;
    }
}
