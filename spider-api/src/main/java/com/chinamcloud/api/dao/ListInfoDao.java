package com.chinamcloud.api.dao;

import com.chinamcloud.api.model.ListInfo;
import com.chinamcloud.spider.orm.dao.Dao;
import com.mongodb.client.MongoCollection;
import org.springframework.stereotype.Component;
import static com.mongodb.client.model.Filters.*;

@Component(value = "listInfoDao")
public class ListInfoDao extends Dao {

    private MongoCollection<ListInfo> collection;

    {
        collection = getCollection("news_list", ListInfo.class);
    }


    public boolean deleteByUrl(String url) {
        try {
            collection.deleteOne(eq("urlMd5", url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}
