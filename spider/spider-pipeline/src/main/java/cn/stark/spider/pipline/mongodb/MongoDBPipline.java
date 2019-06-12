package cn.stark.spider.pipline.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.List;

public class MongoDBPipline<E> {

    private MongoCollection<E> mongoCollection;

    public MongoDBPipline(Class<E> c) {
        init(c);
    }

    private void init(Class<E> c) {
        MongoDatabase mongoDatabase = MongoDBClient.getDatabase("douban");
        this.mongoCollection = mongoDatabase.getCollection("movie", c);
    }

    public void add(E object) {
        mongoCollection.insertOne(object);
    }

    public void add(List<E> objects) {
        mongoCollection.insertMany(objects);
    }
}
