package com.chinamcloud.api.dao;

import com.chinamcloud.spider.orm.dao.Dao;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Component(value = "newsListDao")
public class NewsListDao extends Dao {


    private MongoCollection<Map<String, String>> collection;

    {
        collection = getCollection("news_list", Map.class);
    }


    public String add(Map<String, String> listInfo) {
        try {
            String id;
            if (listInfo.get("urlMd5") == null) {
                Date date = new Date();
                id = String.valueOf((date.getTime() * 100) + (long)(Math.random() * 100));
                listInfo.put("urlMd5", String.valueOf(id));
            } else {
                id = listInfo.get("urlMd5");
            }
            collection.insertOne(listInfo);
            return String.valueOf(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean delete(String url) {
        try {
            collection.deleteOne(eq("urlMd5", url));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Map<String, String> getByUrl(String urlMd5) {
        try {
            MongoCursor<Map<String, String>> taskMongoCursor =
                    collection.find(and(eq("urlMd5", urlMd5)
                    )).iterator();
            while (taskMongoCursor.hasNext()) {
                return taskMongoCursor.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, String>> getAll() {
        List<Map<String, String>> siteTasks = new ArrayList<>();
        try {
            MongoCursor<Map<String, String>> taskMongoCursor =
                    collection.find().iterator();
            while (taskMongoCursor.hasNext()) {
                siteTasks.add(taskMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return siteTasks;
    }



}
