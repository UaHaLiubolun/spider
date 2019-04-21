package com.chinamcloud.spider.weixin.process;

import com.chinamcloud.spider.weixin.util.WeiXinRequestGenerate;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListProcess implements IWeiXinProcess {

    private String baseUrl = "https://mp.weixin.qq.com";

    @Override
    public void process(Page page) {
        List<Request> requests = getRequests(page);
        if (requests == null) return;
        requests.stream().forEach(
                s -> page.addTargetRequest(s)
        );
    }

    private List<Request> getRequests(Page page) {
        String rawText = page.getRawText();
        String regex = "var msgList = \\{(.*)\\]\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rawText);
        String biz = getBiz(page.getRawText());
        if (matcher.find()) {
            String result = matcher.group(1);
            result = "{" + result + "]}";
            System.out.println(result);
            Json json = new Json(result);
            List<String> contentList = json.jsonPath("$.list[*]").all();
            if (contentList == null || contentList.size() == 0) return null;
            List<Request> requests = new ArrayList<>(contentList.size());
            String gzhId = page.getRequest().getExtra("gzhId").toString();
            String gzhName = page.getRequest().getExtra("gzhName").toString();
            String classification = page.getRequest().getExtra("classification").toString();
            for (String content:
                 contentList) {
                Json contentJson = new Json(content);
                String url = baseUrl +
                        contentJson.jsonPath("$.app_msg_ext_info.content_url").get()
                                .replace("amp;", "");
                String date = contentJson.jsonPath("$.comm_msg_info.datetime").get();
                String imgUrl = contentJson.jsonPath("$.app_msg_ext_info.cover").get();
                String digest = contentJson.jsonPath("$.app_msg_ext_info.digest").get();
                Request request = WeiXinRequestGenerate.contentRequest(url, gzhId, classification, gzhName);
                request.putExtra("date", date);
                request.putExtra("imgUrl", imgUrl);
                request.putExtra("digest", digest);
                request.putExtra("biz", biz);
                request.putExtra("contentId", date);
                requests.add(request);


                // 子文章
                List<String> multContent = contentJson.jsonPath("$.app_msg_ext_info.multi_app_msg_item_list[*]").all();
                if (multContent != null && multContent.size() > 0) {
                    for (String mutiContent:
                         multContent) {
                        Json mutiContentJson = new Json(mutiContent);
                        String mUrl = baseUrl + mutiContentJson.jsonPath("$.content_url").get().replace("amp;", "");
                        String mDate = mutiContentJson.jsonPath("$.title").get();
                        String mImgUrl = mutiContentJson.jsonPath("$.cover").get();
                        String mDigest = mutiContentJson.jsonPath("$.digest").get();
                        Request mRequest = WeiXinRequestGenerate.contentRequest(mUrl, gzhId, classification, gzhName);
                        mRequest.putExtra("date", date);
                        mRequest.putExtra("contentId", mDate);
                        mRequest.putExtra("imgUrl", mImgUrl);
                        mRequest.putExtra("digest", mDigest);
                        mRequest.putExtra("biz", biz);
                        requests.add(mRequest);
                    }
                }

            }
            return requests;
        }
        return null;
    }

    private String getBiz(String rawText) {
        String bizRegex = "var biz = \"(.*)\" \\|\\|";
        Pattern pattern = Pattern.compile(bizRegex);
        Matcher matcher = pattern.matcher(rawText);
        if (matcher.find()) {
            String s = matcher.group(1);
            return s;
        }
        return null;
    }
}
