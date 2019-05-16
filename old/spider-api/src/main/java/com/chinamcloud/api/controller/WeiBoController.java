package com.chinamcloud.api.controller;

import com.alibaba.fastjson.JSON;
import com.chinamcloud.api.CodeResult;
import com.chinamcloud.api.model.weibo.Pics;
import com.chinamcloud.api.model.weibo.WeiBoModel;
import com.chinamcloud.spider.search.util.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class WeiBoController {

    private String url = "https://m.weibo.cn/api/container/getIndex?page_type=searchall&containerid=";

    private String param = "100103type=60&t=0&q=";

    private Downloader downloader = new HttpClientDownloader();


    @GetMapping("/weibo/getHotNews")
    public CodeResult getHotNews(@RequestParam(value = "keyword") String kw) throws Exception {
        String p = param + kw;
        String u = url + URLEncoder.encode(p);
        Page page = downloader.download(new Request(u), Site.me().toTask());
        List<String> list = page.getJson().jsonPath("$.data.cards[0].card_group").all();
        List<Map<String, Object>> r = new LinkedList<>();
        for (String s:
             list) {
            try {
                Map<String, Object> result = new HashMap<>();
                WeiBoModel model = JSON.parseObject(s, WeiBoModel.class);
                result.put("author", model.getMblog().getUser().getScreenName());
                result.put("description", model.getMblog().getText());
                result.put("goodNum", model.getMblog().getAttitudesCount());
                result.put("commNum", model.getMblog().getCommentsCount());
                result.put("tranNum", model.getMblog().getRepostsCount());
                result.put("pubTime", TimeUtil.tranTime(model.getMblog().getCreatedAt(), "(\\d+-\\d+)", "yyyy-MM-dd"));
                if (model.getMblog().getPics() != null) {
                    List<String> pics = model.getMblog().getPics().stream().map(Pics::getUrl).collect(Collectors.toList());
                    result.put("pics", pics);
                }
                r.add(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return CodeResult.successResult("ok", r);
    }
}
