package cn.stark.spider.process.douban.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Date;
import java.util.List;

public class Movie {

    private List<String> directors;

    private String rate;

    private int cover_x;

    private String star;

    private String title;

    private String url;

    private List<String> casts;

    private String cover;

    @JSONField(name = "id")
    private String movie_id;

    private int cover_y;

    private Date date = new Date();

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getCover_x() {
        return cover_x;
    }

    public void setCover_x(int cover_x) {
        this.cover_x = cover_x;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getCasts() {
        return casts;
    }

    public void setCasts(List<String> casts) {
        this.casts = casts;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public int getCover_y() {
        return cover_y;
    }

    public void setCover_y(int cover_y) {
        this.cover_y = cover_y;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
