package cn.stark.spider.process.douban.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
public class Movie {

    private List<String> directors = new LinkedList<>();

    private String rate;

    private int cover_x;

    private String star;

    private String title;

    private String url;

    private List<String> casts = new LinkedList<>();

    private String cover;

    @JSONField(name = "id")
    private String movie_id;

    private int cover_y;

    private Date date = new Date();

    private List<String> tags = new LinkedList<>();

    private List<String> genres = new LinkedList<>();

    private List<String> countries = new LinkedList<>();

    private String year_range;
}
