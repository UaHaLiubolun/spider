package com.chinamcloud.spider.orm.dao;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapDao extends Dao {

    private MongoCollection<Map> collection;


    public MapDao(String collectionName) {
        this.collection = getCollection(collectionName, Map.class);
    }

    public boolean add(Map map) {
        try {
            collection.insertOne(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Map> getAll() {
        List<Map> maps = new ArrayList<>();
        try {
            MongoCursor<Map> taskMongoCursor =
                    collection.find().iterator();
            while (taskMongoCursor.hasNext()) {
                maps.add(taskMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maps;
    }
}
