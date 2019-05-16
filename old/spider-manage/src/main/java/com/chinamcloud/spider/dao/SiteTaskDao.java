package com.chinamcloud.spider.dao;

import com.chinamcloud.spider.model.SiteTask;
import com.chinamcloud.spider.orm.dao.Dao;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;


public class SiteTaskDao extends Dao {

    private MongoCollection<SiteTask> collection;

    {
        collection = getCollection("news_list_task", SiteTask.class);
    }


    public boolean add(SiteTask siteTask) {
        try {
            collection.insertOne(siteTask);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SiteTask> getAll() {
        List<SiteTask> siteTasks = new ArrayList<>();
        try {
            MongoCursor<SiteTask> taskMongoCursor =
                    collection.find().iterator();
            while (taskMongoCursor.hasNext()) {
                siteTasks.add(taskMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return siteTasks;
    }


    public boolean modifyByUrl(String domain, SiteTask siteTask) {
        try {
            UpdateResult updateResult =
                    collection.updateOne(eq("urlMd5", domain),
                            combine(
                                    set("threadNum", siteTask.getThreadNum()),
                                    set("interval", siteTask.getInterval()),
                                    set("pageModels", siteTask.getPageModels()),
                                    set("on", siteTask.isOn()),
                                    set("test", siteTask.isTest()),
                                    set("startUrls", siteTask.getStartUrls()),
                                    set("type", siteTask.getType()),
                                    set("charset", siteTask.getCharset())
                            ));
            if (updateResult.getModifiedCount() == 1) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SiteTask> getOn() {
        List<SiteTask> siteTasks = new ArrayList<>();
        try {
            MongoCursor<SiteTask> taskMongoCursor =
                    collection.find(and(eq("on", true)
//                            eq("run", false)
                    )).iterator();
            while (taskMongoCursor.hasNext()) {
                siteTasks.add(taskMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return siteTasks;
    }

    public List<SiteTask> getByDomain(String domian) {
        List<SiteTask> siteTasks = new ArrayList<>();
        try {
            MongoCursor<SiteTask> taskMongoCursor =
                    collection.find(and(eq("site.domain", domian)
                    )).iterator();
            while (taskMongoCursor.hasNext()) {
                siteTasks.add(taskMongoCursor.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return siteTasks;
    }


    public SiteTask getByUrl(String url) {
        try {
            MongoCursor<SiteTask> taskMongoCursor =
                    collection.find(and(eq("url", url)
                    )).iterator();
            while (taskMongoCursor.hasNext()) {
                return taskMongoCursor.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean startRun(String domain, boolean run) {
        boolean result = false;
        try {
            UpdateResult updateResult =
                    collection.updateOne(eq("site.domain", domain), combine(set("run", run)));
            result = updateResult.getMatchedCount() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean endRun(String domain) {
        boolean result = false;
        try {
            int now = (int)(new Date().getTime() / 1000);
            UpdateResult updateResult =
                    collection.updateOne(eq("site.domain", domain),
                            combine(
                                    set("run", false),
                                    set("lastTime", now)));
            result = updateResult.getMatchedCount() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean resetLastTime(String urlMd5) {
        boolean result = false;
        try {
            UpdateResult updateResult =
                    collection.updateOne(eq("urlMd5", urlMd5),
                            combine(
                                    set("lastTime", 0)));
            result = updateResult.getMatchedCount() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除siteTask
     * @param urlMd5
     * @return
     */
    public boolean deleteByUrlMd5(String urlMd5) {
        try {
            collection.deleteOne(eq("urlMd5", urlMd5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void updateLastTimes(String[] urls) {
        try {
            int now = (int)(new Date().getTime() / 1000);
            collection.updateMany(in("url", urls),
                            combine(
                                    set("lastTime", now)
                            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
