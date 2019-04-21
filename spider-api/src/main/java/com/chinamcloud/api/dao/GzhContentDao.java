package com.chinamcloud.api.dao;

import com.chinamcloud.spider.orm.dao.Dao;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;

import java.util.*;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.where;


public class GzhContentDao extends Dao {


    private MongoCollection<Map<String, String>> collection;

    {
        collection = getCollection("wei_xin_content", Map.class);
    }

    public Map<String, Integer> getGzhCount() {
        AggregateIterable output = collection.aggregate(Arrays.asList(
                Aggregates.group("$sourceId", sum("total", 1))
        ));
        MongoCursor<Map<String, Object>> cursor = output.iterator();
        Map<String, Integer> result = new HashMap<>();
        while (cursor.hasNext()) {
            Map<String, Object> map = cursor.next();
            result.put(map.get("_id").toString(), Integer.parseInt(map.get("total").toString()));
        }

        return result;
    }

    public List<Map<String, String>> getContentBySourceId(int sourceId) {
        MongoCursor<Map<String, String>> mapMongoCursor =
                collection.find(eq("sourceId", sourceId)).sort(new BasicDBObject("createdAt", -1)).limit(10)
                .iterator();
        List<Map<String, String>> list = new ArrayList<>();
        while (mapMongoCursor.hasNext()) {
            list.add(mapMongoCursor.next());
        }
        return list;
    }

    public void insert(Map map) {
        collection.insertOne(map);
    }

}
