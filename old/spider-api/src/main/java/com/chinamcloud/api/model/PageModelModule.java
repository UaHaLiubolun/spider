package com.chinamcloud.api.model;

import com.chinamcloud.spider.model.PageModel;
import org.bson.types.ObjectId;

public class PageModelModule {

    private ObjectId id;

    private String name;

    private PageModel pageModel;


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }
}
