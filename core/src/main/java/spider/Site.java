package spider;

import org.apache.http.HttpHost;

import java.util.HashMap;
import java.util.Map;

public class Site {

    // 域名
    private String domain;
    // User-Agent
    private String userAgent;
    // 默认头信息
    private Map<String, String> defaultHeaders = new HashMap<String, String>();
    // 编码
    private String charset = "utf8";
    // 每次任务间隔时间 ms
    private int sleepTime = 0;
    // 超时时间
    private int timeOut = 5000;
    // 是否使用自动代理
    private boolean isProxy = false;
    // 使用手动代理
    private HttpHost httpHost;
    // 重试次数
    private int retryCount = 0;

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public boolean isProxy() {
        return isProxy;
    }

    public void setProxy(boolean proxy) {
        isProxy = proxy;
    }

    public HttpHost getHttpHost() {
        return httpHost;
    }

    public void setHttpHost(HttpHost httpHost) {
        this.httpHost = httpHost;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Map<String, String> getDefaultHeaders() {
        return defaultHeaders;
    }

    public void setDefaultHeaders(Map<String, String> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
