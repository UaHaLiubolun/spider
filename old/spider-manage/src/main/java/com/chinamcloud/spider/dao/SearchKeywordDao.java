package com.chinamcloud.spider.dao;

import com.chinamcloud.spider.orm.dao.Dao;
import com.mongodb.client.MongoCollection;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class SearchKeywordDao extends Dao {
    private MongoCollection<Map<String, Object>> collection;

    private DaoUtil<Map<String, Object>> daoUtil;

    {
        collection = getCollection("search_keyword", Map.class);
        daoUtil = new DaoUtil<>(collection);
    }

    public List<Map<String, Object>> getAll() {
        return daoUtil.getAll(collection);
    }

    public boolean add(Map<String, Object> map) {
        try {
            collection.insertOne(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateLastTimes(String[] kws) {
        try {
            int now = (int)(new Date().getTime() / 1000);
            collection.updateMany(in("kw", kws),
                    combine(
                            set("lastTime", now)
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
