package spider;

import spider.filter.PageFilter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Request implements Serializable {

    private static final long serialVersionUID = 2062192774891352043L;

    public Request() {}

    public Request(String url, PageFilter filter) {
        this.filter = filter;
        this.url = url;
    }

    private PageFilter filter;

    private String url;

    private String method;
    // Cookie
    private Map<String, String> cookies = new HashMap<String, String>();
    // 头信息
    private Map<String, String> headers = new HashMap<String, String>();
    // body信息 post或者put时有效
    private HttpRequestBody requestBody;
    // meta信息 主要用户传递信息 从上一个filter传递信息下一个filter
    private Map<String, Object> meta;

    public HttpRequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(HttpRequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public PageFilter getFilter() {
        return filter;
    }

    public void setFilter(PageFilter filter) {
        this.filter = filter;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }
}
