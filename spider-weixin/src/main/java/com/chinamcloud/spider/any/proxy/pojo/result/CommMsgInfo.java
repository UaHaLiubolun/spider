package com.chinamcloud.spider.any.proxy.pojo.result;

public class CommMsgInfo {

    private long id;
    private int type;
    private String fakeid;
    private int status;
    private String content;
    private int datetime;

    public int getDatetime() {
        return datetime;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFakeid() {
        return fakeid;
    }

    public void setFakeid(String fakeid) {
        this.fakeid = fakeid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
