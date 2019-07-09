package cn.stark.spider.process;

import cn.stark.spider.common.Page;
import cn.stark.spider.common.spider.PageProcesser;
import cn.stark.spider.process.douban.pojo.MovieResult;
import com.alibaba.fastjson.JSON;


public class AutoProcess implements PageProcesser {

    private MoviePipline mongoDBPipline = new MoviePipline();

    @Override
    public void process(Page page) {
        try {
            String s = new String(page.getBytes(), page.getCharset() ==  null ? "utf-8" : page.getCharset());
            MovieResult movie = JSON.parseObject(s, MovieResult.class);
            mongoDBPipline.add(movie.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
