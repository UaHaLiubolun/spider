package com.chinamcloud.spider.sina.process;

import com.chinamcloud.spider.sina.utils.RequestGenerate;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SinaHomeProcess implements ISinaProcess {

    @Override
    public void process(Page page) {

        String domainRegex = "\\$CONFIG\\[\\'domain\\'\\]=\\'(\\d+)\\'";
        String personId = page.getRequest().getExtra("personId").toString();
        Pattern pattern = Pattern.compile(domainRegex);
        Matcher matcher = pattern.matcher(page.getRawText());
        if (matcher.find()) {
            Request request = RequestGenerate.person(matcher.group(1), personId);
            page.addTargetRequest(request);
        }
    }


}
