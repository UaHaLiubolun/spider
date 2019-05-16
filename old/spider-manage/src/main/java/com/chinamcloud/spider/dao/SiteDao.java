package com.chinamcloud.spider.dao;

import com.chinamcloud.spider.orm.dao.Dao;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import us.codecraft.webmagic.Site;

import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.*;


public class SiteDao extends Dao {

    private MongoCollection<Site> collection;

    {
        collection = getCollection("site", Site.class);
    }


    public boolean add(Site siteTask) {
        try {
            collection.insertOne(siteTask);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Site> getAll() {
        List<Site> siteTasks = new ArrayList<>();
        try {
            MongoCursor<Site> taskMongoCursor =
                    collection.find().iterator();
            while (taskMongoCursor.hasNext()) {
                siteTasks.add(taskMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return siteTasks;
    }

    public Site getByDomain(String domain) {
        try {
            MongoCursor<Site> taskMongoCursor =
                    collection.find(eq("domain", domain)).iterator();
            while (taskMongoCursor.hasNext()) {
                return taskMongoCursor.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
