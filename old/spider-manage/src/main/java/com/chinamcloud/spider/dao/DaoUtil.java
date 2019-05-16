package com.chinamcloud.spider.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class DaoUtil<E> {

    private MongoCollection<E> collection;

    public DaoUtil(MongoCollection<E> collection) {
        this.collection = collection;
    }

    public  List<E> getAll(MongoCollection<E> collection) {
        List<E> searchKws = new ArrayList<>();
        try {
            MongoCursor<E> taskMongoCursor =
                    collection.find().iterator();
            while (taskMongoCursor.hasNext()) {
                searchKws.add(taskMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchKws;
    }
}
