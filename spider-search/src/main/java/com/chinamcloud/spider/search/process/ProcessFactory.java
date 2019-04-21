package com.chinamcloud.spider.search.process;

import com.chinamcloud.spider.search.util.SearchConst;
import com.chinamcloud.spider.search.util.Site;

public class ProcessFactory {

    static ISearchProcess getInstance(String type) {
        if (type.equals(Site.BAI_DU.getType())) {
            return new BaiDuSearchProcess();
        }
        if (type.equals(SearchConst.CONTENT_TYPE)) {
            return new ContentProcess();
        }
        if (type.equals(Site.SO_GOU.getType())) {
            return new SoGouSearchProcess();
        }
        if (type.equals(Site.B_TIME.getType())) {
            return new BTimeSearchProcess();
        }
        if (type.equals(Site.SINA_NEWS.getType())) {
            return new SinaSearchProcess();
        }
        return null;
    }
}
