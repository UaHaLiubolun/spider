package com.chinamcloud.spider.sina.pipeline;

import com.chinamcloud.spider.orm.mongo.MongoJDBC;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

public class SinaPipeline implements Pipeline {

    private MongoDatabase mongoDatabase = MongoJDBC.getDataBase("Sina");

    private MongoCollection<Map> detailDao = mongoDatabase.getCollection("weibo_detail", Map.class);

    private MongoCollection<Map> reviewDao = mongoDatabase.getCollection("weibo_review", Map.class);

    private MongoCollection<Map> rePostDao = mongoDatabase.getCollection("weibo_re_post", Map.class);

    private MongoCollection<Map> personDao = mongoDatabase.getCollection("weibo_person", Map.class);

    @Override
    public void process(ResultItems resultItems, Task task) {
        String type = resultItems.get("type");
        if(type == null) return;
        switch (type) {
            case "detail":
                detailDao.insertOne(resultItems.get("detail"));
                break;
            case "review":
                List<Map> maps = resultItems.get("review");
                if (maps.size() == 0) return;
                reviewDao.insertMany(maps);
                break;
            case "rePost":
                List<Map> maps1 = resultItems.get("rePost");
                if (maps1.size() == 0) return;
                rePostDao.insertMany(maps1);
                break;
            case "person":
                Map<String, String> map = resultItems.get("person");
                personDao.insertOne(map);
                break;
        }
    }
}
