package com.chinamcloud.spider.sina.process;

import com.alibaba.fastjson.JSON;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Json;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SinaDetailProcess implements ISinaProcess{

    @Override
    public void process(Page page) {
        String rawText = page.getRawText();
        rawText = rawText.replace(" ", "").replace("\n", "");
        String regex = "render_data=\\[\\{\"status\":(.*?)\\]\\[0\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rawText);
        String contentJson = null;
        if (matcher.find()) {
            contentJson = matcher.group(1);
        }
        if (contentJson == null) return;
        Json content = new Json(contentJson);
        Map result = new HashMap();

        result.put("comments_count", content.jsonPath("$.comments_count").get());
        result.put("id", content.jsonPath("$.id").get());
        result.put("mid", content.jsonPath("$.mid").get());
        result.put("source", content.jsonPath("$.source").get());
        result.put("reposts_count", content.jsonPath("$.reposts_count").get());
        result.put("attitudes_count", content.jsonPath("$.attitudes_count").get());
        result.put("text", content.jsonPath("$.text").get());
        result.put("pics", content.jsonPath("$.pics[*].url").all());
        result.put("user", JSON.parse(content.jsonPath("$.user").get()));

        page.getResultItems().put("type", "detail");
        page.getResultItems().put("detail", result);
    }
}
