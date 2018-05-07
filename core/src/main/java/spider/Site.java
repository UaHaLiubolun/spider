package spider;

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
    // 每次任务间隔时间
    private int sleepTime = 5000;
    // 超时重试次数
    private int retryTimes = 0;
    // 超时时间
    private int timeOut = 5000;

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

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
