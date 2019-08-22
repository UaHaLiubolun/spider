/*
 * @projectName spider
 * @package cn.stark.spider.common.bean
 * @className cn.stark.spider.common.bean.DouBanRequest
 * @copyright Copyright 2019 Thuisoft, Inc. All rights reserved.
 */
package cn.stark.spider.common.bean;

import cn.stark.spider.common.Request;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DouBanRequest
 * @description TODO
 * @author liubolun
 * @date 2019年08月22日 11:13
 * @version 3.0.0
 */
@Data
public class DouBanRequest implements Serializable {

    private static final long serialVersionUID = 2062192774891252043L;

    private final static String BASE_URL = "http://movie.douban.com/j/new_search_subjects?";

    private String tags = "";

    /**
     * 排序
     * S 评分
     * T 标记
     * U 热门
     * R 最新
     */
    private String sort = "U";

    private String range = "5,10";

    private String genres;

    private String countries;

    private String year_range;

    public List<Request> generateRequest(int num) {
        List<Request> requests = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            Request request = new Request(generateUrl(i * 20));
            request.putExtra("info", this);
            requests.add(request);
        }
        return requests;
    }

    private String generateUrl(int start) {
        String url =  BASE_URL + "sort=" + sort +  "&range=" + range + "&tags=" + tags +  "&start=" + start;
        if (StringUtils.isNotBlank(countries)) {
            url += ("&countries=" + countries);
        }
        if (StringUtils.isNotBlank(genres)) {
            url += ("&genres=" + genres);
        }
        if (StringUtils.isNotBlank(year_range)) {
            url += ("&year_range=" + year_range);
        }
        return url;
    }

}
