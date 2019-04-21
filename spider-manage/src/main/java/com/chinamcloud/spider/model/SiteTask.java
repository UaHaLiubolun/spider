package com.chinamcloud.spider.model;


import org.bson.types.ObjectId;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;

import java.util.List;

public class SiteTask {

    private String urlMd5;

    private ObjectId id;

    private String url;

    private List<String> startUrls;

    private String charset = "utf-8";

    private String name;

    private String method = "get";

    private String contentType;

    private String body;
    // 线程池大小
    private int threadNum = 1;
    // 间隔时间 s
    private int interval = 60 * 60;
    // 规则
    private List<PageModel> pageModels;
    // 上一次执行时间
    private int lastTime;
    // 是否启用
    private boolean isOn = true;
    // 是否正在运行
    private boolean isRun = false;
    // 是否处于测试阶段（测试阶段）
    private boolean isTest = false;

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    private String source;

    private String spiderType;

    private String type;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSpiderType() {
        return spiderType;
    }

    public void setSpiderType(String spiderType) {
        this.spiderType = spiderType;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMd5 = urlMd5;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getStartUrls() {
        return startUrls;
    }

    public void setStartUrls(List<String> startUrls) {
        this.startUrls = startUrls;
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public int getLastTime() {
        return lastTime;
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PageModel> getPageModels() {
        return pageModels;
    }

    public void setPageModels(List<PageModel> pageModels) {
        this.pageModels = pageModels;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
