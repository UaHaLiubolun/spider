package com.chinamcloud.spider.dao;

import com.chinamcloud.spider.orm.dao.Dao;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;


import java.util.Map;

public class NewsListDao extends Dao {


    private MongoCollection<Map<String, String>> collection;

    {
        collection = getCollection("news_list", Map.class);
    }


    public String getNameByUrl(String url) {
        Map<String, String> map = getByUrl(url);
        if (map == null) return null;
        return map.get("name");
    }

    public Map<String, String> getByUrl(String url) {
        try {
            MongoCursor<Map<String, String>> taskMongoCursor =
                    collection.find(
                           eq("url", url)
                    ).iterator();
            while (taskMongoCursor.hasNext()) {
                return taskMongoCursor.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
