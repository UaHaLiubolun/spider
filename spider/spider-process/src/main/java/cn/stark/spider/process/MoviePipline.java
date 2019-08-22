package cn.stark.spider.process;

import cn.stark.spider.pipline.mongodb.MongoDBClient;
import cn.stark.spider.process.douban.pojo.Movie;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;

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
                updateTags(cursor.next(), m);
                mongoCollection.replaceOne(eq("movie_id", m.getMovie_id()), m);
            } else {
                mongoCollection.insertOne(m);
            }
        }
    }

    private void updateTags(Movie oldMovie, Movie newMovie) {
        if (StringUtils.isBlank(oldMovie.getYear_range())) {
            newMovie.setYear_range(oldMovie.getYear_range());
        }
        List<String> oldTags = oldMovie.getTags();
        List<String> newTags = newMovie.getTags();
        for (String s : oldTags) {
            if (!newTags.contains(s)) {
                newTags.add(s);
            }
        }
        List<String> oldC = oldMovie.getCountries();
        List<String> newC = newMovie.getCountries();
        for (String s : oldC) {
            if (!newC.contains(s)) {
                newC.add(s);
            }
        }
        List<String> oldG = oldMovie.getGenres();
        List<String> newG = newMovie.getGenres();
        for (String s : oldG) {
            if (!newG.contains(s)) {
                newG.add(s);
            }
        }
    }
}
