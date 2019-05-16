package com.chinamcloud.spider.test;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class NewsSearchSpider {

    public static void main(String[] args) throws Exception {
        BaiDuSpider baiDuSpider = new BaiDuSpider();
        List<Map<String, Object>> list = baiDuSpider.spider("赵丽颖");
        System.out.println(JSON.toJSONString(list));
    }

}
