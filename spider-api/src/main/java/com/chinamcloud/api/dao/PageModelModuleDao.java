package com.chinamcloud.api.dao;

import com.chinamcloud.api.model.PageModelModule;
import com.chinamcloud.spider.orm.dao.Dao;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Component("pageModelModuleDao")
public class PageModelModuleDao extends Dao {

    private MongoCollection<PageModelModule> collection;

    {
        collection = getCollection("pageModel", PageModelModule.class);
    }


    public boolean add(PageModelModule listInfo) {
        try {
            collection.insertOne(listInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteByName(String name) {
        try {
            collection.deleteOne(eq("name", name));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PageModelModule> getAll() {
        List<PageModelModule> siteTasks = new ArrayList<>();
        try {
            MongoCursor<PageModelModule> taskMongoCursor =
                    collection.find().iterator();
            while (taskMongoCursor.hasNext()) {
                siteTasks.add(taskMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return siteTasks;
    }

    public PageModelModule getByName(String name) {
        try {
            MongoCursor<PageModelModule> taskMongoCursor =
                    collection.find(and(eq("name", name)
                    )).iterator();
            while (taskMongoCursor.hasNext()) {
                return taskMongoCursor.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
