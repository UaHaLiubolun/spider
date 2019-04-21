package com.chinamcloud.api.model;

import java.io.Serializable;
import java.util.List;

public class SiteTree implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private long parentId;

    private String name;

    private int sourceId;

    private boolean isLeaf;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    private List<SiteTree> children;

    public List<SiteTree> getChildren() {
        return children;
    }

    public void setChildren(List<SiteTree> children) {
        this.children = children;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
