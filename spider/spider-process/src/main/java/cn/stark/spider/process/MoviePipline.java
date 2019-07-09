package cn.stark.spider.process;

import cn.stark.spider.pipline.mongodb.MongoDBClient;
import cn.stark.spider.process.douban.pojo.Movie;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.Collections;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MoviePipline {


    private MongoCollection<Movie> mongoCollection;

    public MoviePipline() {
        init();
    }

    private void init() {
        MongoDatabase mongoDatabase = MongoDBClient.getDatabase("douban");
        this.mongoCollection = mongoDatabase.getCollection("movie_test", Movie.class);
    }

    public void add(List<Movie> objects) {
        for (Movie m : objects) {
            FindIterable<Movie> movies = mongoCollection.find(eq("movie_id", m.getMovie_id()));
            MongoCursor<Movie> cursor = movies.iterator();
            if (cursor.hasNext()) {
                mongoCollection.replaceOne(eq("movie_id", m.getMovie_id()), m);
            } else {
                mongoCollection.insertOne(m);
            }
        }
    }
}
