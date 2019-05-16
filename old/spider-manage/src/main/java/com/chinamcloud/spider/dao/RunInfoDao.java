package com.chinamcloud.spider.dao;


import com.chinamcloud.spider.model.SpiderRunInfo;
import com.chinamcloud.spider.orm.dao.Dao;
import com.mongodb.client.MongoCollection;

public class RunInfoDao extends Dao{

    private MongoCollection<SpiderRunInfo> collection;

    {
        collection = getCollection("spiderRunInfo", SpiderRunInfo.class);
    }

    
    public boolean add(SpiderRunInfo spiderRunInfo) {
        try {
            collection.insertOne(spiderRunInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
