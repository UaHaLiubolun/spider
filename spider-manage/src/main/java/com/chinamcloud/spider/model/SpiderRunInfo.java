package com.chinamcloud.spider.model;


import java.util.Date;

public class SpiderRunInfo {

    public SpiderRunInfo(String domain, long pageCount, Date startTime, Date endTime) {
        this.domain = domain;
        this.pageCount = pageCount;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private String domain;

    private long pageCount;

    private Date startTime;

    private Date endTime;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
