package cn.stark.spider.process;

import cn.stark.spider.common.Page;
import cn.stark.spider.common.Request;
import cn.stark.spider.common.bean.DouBanRequest;
import cn.stark.spider.common.spider.PageProcesser;
import cn.stark.spider.process.douban.pojo.MovieResult;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AutoProcess implements PageProcesser {

    private MoviePipline mongoDBPipline = new MoviePipline();

    @Override
    public void process(Page page) {
        try {
            Request request = page.getRequest();
            Object object = request.getExtra("info");
            String s = new String(page.getBytes(), page.getCharset() ==  null ? "utf-8" : page.getCharset());
            MovieResult movie = JSON.parseObject(s, MovieResult.class);
            if (object instanceof DouBanRequest) {
                DouBanRequest douBanRequest = (DouBanRequest) request.getExtra("info");
                movie.getData().forEach(m -> {
                    m.setTags(new ArrayList<>(Arrays.asList(douBanRequest.getTags().split(","))));
                    if (StringUtils.isNotBlank(douBanRequest.getGenres())) {
                        m.setGenres(Collections.singletonList(douBanRequest.getGenres()));
                    }
                    if (StringUtils.isNotBlank(douBanRequest.getCountries())) {
                        m.setCountries(Collections.singletonList(douBanRequest.getCountries()));
                    }
                    if (StringUtils.isNotBlank(douBanRequest.getYear_range())) {
                        m.setYear_range(douBanRequest.getYear_range());
                    }
                });
            }
            mongoDBPipline.add(movie.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
