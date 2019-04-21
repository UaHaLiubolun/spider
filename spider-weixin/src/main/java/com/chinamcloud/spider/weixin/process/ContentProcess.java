package com.chinamcloud.spider.weixin.process;

import com.alibaba.fastjson.JSON;
import us.codecraft.webmagic.Page;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentProcess implements IWeiXinProcess {

    @Override
    public void process(Page page) {
        String title = page.getHtml().xpath("//h2[@class='rich_media_title']/text()").get();
        String content = page.getHtml().xpath("//div[@class='rich_media_content']/newsText()").get();

        String gzhId;
        if (page.getRequest().getExtra("gzhId") != null) {
            gzhId = page.getRequest().getExtra("gzhId").toString();
        } else {
            gzhId = page.getHtml().xpath("//div[@id='js_profile_qrcode']/div/p[1]/span/text()").get();
        }

        String gzhName;
        if (page.getRequest().getExtra("gzhName") != null) {
            gzhName = page.getRequest().getExtra("gzhName").toString();
        } else {
            gzhName = page.getHtml().xpath("//div[@id=\"js_profile_qrcode\"]/div/strong/text()").get();
        }

        List<String> img = page.getHtml().xpath("//div[@class='rich_media_content']//img/@data-src").all();

        Map<String, Object> result = new HashMap<>();
        Object biz = page.getRequest().getExtra("biz");
        String originUrl;
        if (biz != null) {
            originUrl = tranUrl(page.getRequest().getUrl(), biz.toString());
        } else originUrl = page.getRequest().getUrl();
        result.put("subject", title.trim());
        long pubTime;
        try {
            pubTime = Long.parseLong(page.getRequest().getExtra("date").toString()) * 1000;
        } catch (NumberFormatException e) {
            pubTime = System.currentTimeMillis();
        }
        result.put("pubTime", pubTime);
        result.put("description", content);
        result.put("tbNickName", gzhName);
        result.put("refererUrl", originUrl);
        result.put("pics", img);
        result.put("langType", "ch");
        result.put("source", "WeiXin");
        result.put("createdAt", new Date().getTime());
        result.put("classification", page.getRequest().getExtra("classification"));
        result.put("sourceId", Math.abs(gzhId.hashCode()));
        result.put("isCralwer", 1);
        result.put("cover", page.getRequest().getExtra("imgUrl"));
        result.put("digest", page.getRequest().getExtra("digest"));
        page.getResultItems().put("content", result);
    }

    private String tranUrl(String refererUrl, String biz) {
        HttpURLConnection connection = null;
        StringBuilder sb = new StringBuilder();
        //String baseUrl = "http://219.234.5.17:521/bizapi/getgurl?password=195099&username=853095099";
        String baseUrl = "http://api.bizapi.cn:521/bizapi/getgurl?password=195099&username=853095099";
        String u = baseUrl + "&biz=" + biz + "&url=" + URLEncoder.encode(refererUrl);
        try {
            URL url = new URL(u);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //设置请求连接超时时间
            connection.setConnectTimeout(5000);
            //设置访问时的超时时间
            connection.setReadTimeout(5000);
            InputStream inputStream=null;
            //如果应答码为200的时候，表示成
            if (200 == connection.getResponseCode()) {
                inputStream = connection.getInputStream();
                byte[] bytes = new byte[1024];
                int i = 0;
                while ((i = inputStream.read(bytes)) != -1) {
                    sb.append(new String(bytes,0,i,"utf-8"));
                }
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        String s = sb.toString();
        if (s.equals("")) return refererUrl;
        Map<String, Object> map = (Map<String, Object>) JSON.parseObject(s, Map.class);
        if (Integer.parseInt(map.get("code").toString()) == 1) {
            return map.get("result").toString();
        }
        return refererUrl;
    }
}
