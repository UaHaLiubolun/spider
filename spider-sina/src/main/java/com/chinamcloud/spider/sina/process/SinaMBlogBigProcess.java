package com.chinamcloud.spider.sina.process;

import com.chinamcloud.spider.sina.utils.RequestGenerate;
import com.chinamcloud.spider.sina.utils.SinaUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SinaMBlogBigProcess implements ISinaProcess {


    @Override
    public void process(Page page) {
        String weiboId = page.getRequest().getExtra("weiboId").toString();
        int rePostNum = Integer.parseInt(page.getRequest().getExtra("rePostNum").toString());
        String topId = page.getRequest().getExtra("topId").toString();
        String parentId = page.getRequest().getExtra("parentId").toString();

        Json json = new Json(page.getRawText());
        String html = json.jsonPath("$.data.html").get();
        html = html.replace("\n", "");
        Document htmlSec = Jsoup.parse(html);

        int pageNum = Integer.parseInt(json.jsonPath("$.data.page.pagenum").get());
        if (pageNum == 1 || pageNum == 2) {
            int totalpage = Integer.parseInt(json.jsonPath("$.data.page.totalpage").get());
            if (totalpage > 1) {
                for (int i = 1; i < totalpage; i++) {
                    int currentPage = i + 1;
                    Request request = RequestGenerate.rePost(weiboId, parentId, topId, currentPage, rePostNum);
                    page.addTargetRequest(request);
                }
            }
        }

        Elements elements = htmlSec.getElementsByAttributeValue("class", "list_li S_line1 clearfix");

        List<Map<String, String>> list = new ArrayList<>(elements.size());
        for (Element element : elements) {
            Map<String, String> reviewMap = new HashMap<>();
            reviewMap.put("id", element.attributes().get("mid"));
            reviewMap.put("topId", topId);
            reviewMap.put("parentId", parentId);


            Elements eleWbText = element.getElementsByClass("WB_text");
            // 用户
            Element userEl = eleWbText.get(0).getElementsByTag("a").get(0);
            String userId = userEl.attributes().get("usercard")
                    .replace("id=", "");
            String nicknameId =  SinaUtils.getNicknameId(userEl.attributes().get("href"));
            reviewMap.put("userId", userId);
            reviewMap.put("nicknameId", nicknameId);
            reviewMap.put("nickname", userEl.text());
            reviewMap.put("value", eleWbText.text());

//            page.addTargetRequest(RequestGenerate.home(nicknameId, userId));

            reviewMap.put("value", element.getElementsByAttributeValue("node-type", "text").text());
            Element eleTime = element.getElementsByAttributeValue("class", "WB_from S_txt2").get(0);
            String timeStr = eleTime.text();
            reviewMap.put("putTime", String.valueOf(SinaUtils.transTime(timeStr)));
            String goodNum = element.getElementsByAttributeValue("node-type", "like_status").select("em").get(1).text();
            if (goodNum.equals("赞")) {
                reviewMap.put("goodNum", "0");
            } else {
                reviewMap.put("goodNum", goodNum);
            }

            String rePost = element.getElementsByAttributeValue("class", "clearfix")
                    .get(0).getElementsByTag("li")
                    .get(1).getElementsByTag("a")
                    .get(0).text();
            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(rePost);
            if (matcher.find()) {
                int num = Integer.parseInt(matcher.group(1));
                Request request = RequestGenerate.rePost( element.attributes().get("mid"), weiboId, topId, 1, rePostNum + 1);
                page.addTargetRequest(request);
                reviewMap.put("rePost", String.valueOf(num));
            } else {
                reviewMap.put("rePost", "0");
            }
            reviewMap.put("rePostNum", String.valueOf(rePostNum));
            list.add(reviewMap);
        }

        page.getResultItems().put("type", "rePost");
        page.getResultItems().put("rePost", list);
    }

}
