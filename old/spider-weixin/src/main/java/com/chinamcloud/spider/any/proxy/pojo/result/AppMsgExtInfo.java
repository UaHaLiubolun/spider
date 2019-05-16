package com.chinamcloud.spider.any.proxy.pojo.result;

import java.util.List;

public class AppMsgExtInfo {

    private String title;
    private String digest;
    private int fileid;
    private String contentUrl;
    private String sourceUrl;
    private String cover;
    private int subtype;
    private String author;

    private List<AppMsgExtInfo> multiAppMsgItemList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getSubtype() {
        return subtype;
    }

    public void setSubtype(int subtype) {
        this.subtype = subtype;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<AppMsgExtInfo> getMultiAppMsgItemList() {
        return multiAppMsgItemList;
    }

    public void setMultiAppMsgItemList(List<AppMsgExtInfo> multiAppMsgItemList) {
        this.multiAppMsgItemList = multiAppMsgItemList;
    }
}
