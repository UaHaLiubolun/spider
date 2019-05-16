package com.chinamcloud.spider.dao;

import com.chinamcloud.spider.orm.dao.Dao;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class WeChatDao extends Dao {


    private MongoCollection<Map<String, String>> collection;

    {
        collection = getCollection("we_chat_gzh", Map.class);
    }


    public boolean add(Map<String, String> siteTask) {
        try {
            collection.insertOne(siteTask);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean modify(Map<String, String> siteTask) {
        try {
            UpdateResult updateResult =
                    collection.updateOne(eq("gzhId", siteTask.get("gzhId")),
                    combine(
                            set("name", siteTask.get("name")),
                            set("interval", siteTask.get("interval")),
                            set("on", siteTask.get("on")),
                            set("type", siteTask.get("type"))
                    ));
            return updateResult.getModifiedCount() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Map<String, String>> getAll() {
        List<Map<String, String>> siteTasks = new ArrayList<>();
        try {
            MongoCursor<Map<String, String>> taskMongoCursor = collection.find().iterator();
            while (taskMongoCursor.hasNext()) {
                siteTasks.add(taskMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return siteTasks;
    }

    public boolean deleteByUrl(String url) {
        try {
            collection.deleteOne(eq("gzhId", url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public List<Map<String, String>> getOn() {
        List<Map<String, String>> siteTasks = new ArrayList<>();
        try {
            MongoCursor<Map<String, String>> taskMongoCursor =
                    collection.find(and(eq("on", "true")
                    )).iterator();
            while (taskMongoCursor.hasNext()) {
                siteTasks.add(taskMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return siteTasks;
    }

    public void updateLastTimes(String[] gzhId) {
        try {
            int now = (int)(new Date().getTime() / 1000);
            collection.updateMany(in("gzhId", gzhId),
                    combine(
                            set("lastTime", String.valueOf(now))
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
