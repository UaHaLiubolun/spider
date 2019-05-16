package com.chinamcloud.spider.weixin.pipeline;

import com.chinamcloud.spider.orm.mongo.MongoJDBC;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

public class WeiXinPipeline implements Pipeline {
    private MongoDatabase mongoDatabase = MongoJDBC.getDataBase("spider");

    private MongoCollection<Map> detailDao = mongoDatabase.getCollection("wei_xin_content", Map.class);

    @Override
    public void process(ResultItems resultItems, Task task) {
        if (resultItems.get("content") != null) {
            detailDao.insertOne(resultItems.get("content"));
        }
    }
}
