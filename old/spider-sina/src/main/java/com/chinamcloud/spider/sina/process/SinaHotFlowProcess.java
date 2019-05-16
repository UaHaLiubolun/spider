package com.chinamcloud.spider.sina.process;

import com.alibaba.fastjson.JSON;
import com.chinamcloud.spider.sina.UserAgent;
import com.chinamcloud.spider.sina.cookie.CookieUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Json;

import java.util.List;
import java.util.Map;

public class SinaHotFlowProcess implements ISinaProcess {

    @Override
    public void process(Page page) {
        Json json = page.getJson();
        int ok = Integer.parseInt(json.jsonPath("$.ok").get());
        if (ok == 0) {return;}
        String strings = json.jsonPath("$.data[*]").get();
        String weiboId = page.getRequest().getExtra("weiboId").toString();
        List<Map> maps = JSON.parseArray(strings, Map.class);
        maps.stream().forEach(
                s -> {
                    s.put("weiboId", weiboId);
                }
        );

        page.getResultItems().put("type", "review");
        page.getResultItems().put("review", maps);

        long max_id = Long.parseLong(json.jsonPath("$.data.max_id").get());
        if (max_id == 0) return;

        Request request = new Request();

        request.setUrl("https://m.weibo.cn/comments/hotflow?id=" + weiboId + "&mid=" + weiboId+ "&max_id_type=0&max_id=" + max_id);
        request.setCookies(CookieUtil.getCookie());
        request.putExtra("weiboId", weiboId);
        request.putExtra("ProcessType", "review");
        request.addHeader("User-Agent", UserAgent.getUA());
        request.addHeader("Referer", "https://m.weibo.cn/detail/" + weiboId);
        page.addTargetRequest(request);
    }
}
