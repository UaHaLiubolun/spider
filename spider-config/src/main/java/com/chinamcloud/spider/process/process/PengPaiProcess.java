package com.chinamcloud.spider.process.process;

import com.chinamcloud.spider.handle.ProcessUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PengPaiProcess implements OwnProcess {

    @Override
    public Object process(Page page) {
        String rawText = page.getRawText().replace("\n", "");
        String regex = "<div class=\"newscontent\">(.*)<div id=\"toTop\" class=\"back-top\">";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rawText);
        if (matcher.find()) {
            String regexText = matcher.group(1);
            Html html = new Html(regexText);
            String description = html.xpath("//div[@class='news_txt']/newsText()").get();
            String htmlDescription = html.xpath("//div[@class='news_txt']/outerHtml()").get();
            String subject = html.xpath("//h1/text()").get();
            Long pubTime = System.currentTimeMillis();
            String parentSource;
            List<String> pics = html.xpath("//div[@class='news_txt']//img/@src").all();

            String newsAbout = html.xpath("//div[@class='news_about']/p[1]/allText()").get();
            newsAbout += html.xpath("//div[@class='news_about']/p[2]/allText()").get();
            String timeRegex = "(\\d+-\\d+-\\d+ \\d+:\\d+)";
            Matcher matcher1 = Pattern.compile(timeRegex).matcher(newsAbout);
            if (matcher1.find()) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String timeStr = matcher1.group(1);
                try {
                    pubTime = simpleDateFormat.parse(timeStr).getTime();
                } catch (ParseException e) {

                }
            }
            String sourceRegex = "来源(.*)";
            Matcher sourceMatcher = Pattern.compile(sourceRegex).matcher(newsAbout);
            if (sourceMatcher.find()) {
                parentSource = sourceMatcher.group(1);
                parentSource = parentSource.replaceAll("：", "").replaceAll(":", "");
            } else {
                parentSource = newsAbout.replaceAll(timeRegex, "");
            }

            Map<String, Object> map = new HashMap();
            ProcessUtil.tranToMap(map, page);
            map.put("description", description);
            map.put("subject", subject);
            map.put("putTime", pubTime);
            map.put("parentSource", parentSource);
            map.put("htmlDescription", htmlDescription);
            map.put("pics", pics);
            return map;
        }
        return null;
    }


}
