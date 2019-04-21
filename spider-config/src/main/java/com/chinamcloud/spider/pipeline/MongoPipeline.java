package com.chinamcloud.spider.pipeline;


import com.chinamcloud.spider.orm.dao.MapDao;
import us.codecraft.webmagic.Task;

import java.util.Date;
import java.util.Map;

public class MongoPipeline implements ObjectPipeline{

    private MapDao objectDao;

    public MongoPipeline(String collection) {
        this.objectDao = new MapDao(collection);
    }

    public MongoPipeline() {
        this.objectDao = new MapDao("news");
    }

    @Override
    public void process(Object o, Task task) {
        if (objectDao == null) {
            // TODO 写入同一张表
            objectDao = new MapDao("news");
        }
        Map map = (Map) o;
        map.put("createdAt", new Date().getTime());
        //TODO 语言暂时写死
        map.put("langType", "ch");
        map.put("sourceId", Math.abs(map.get("tbNickName").hashCode()));
        map.put("isCralwer", 1);
        objectDao.add((Map) o);
    }
}
