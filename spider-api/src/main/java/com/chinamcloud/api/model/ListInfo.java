package com.chinamcloud.api.model;

import org.bson.types.ObjectId;

import java.util.List;

public class ListInfo {

    private ObjectId id;

    private String urlMd5;

    private String url;

    private String parentUrl;

    private String name;

    private List<ListInfo> children;

    private boolean isLeaf;

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public List<ListInfo> getChildren() {
        return children;
    }

    public void setChildren(List<ListInfo> children) {
        this.children = children;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMd5 = urlMd5;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
