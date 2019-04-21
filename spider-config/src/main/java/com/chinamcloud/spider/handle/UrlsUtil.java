package com.chinamcloud.spider.handle;

import com.chinamcloud.spider.model.PageModel;
import us.codecraft.webmagic.Page;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlsUtil {



    public static void put(Page page, Object process, PageModel pageModel) {
        if (pageModel.getMultiExtract() != null && pageModel.getMultiExtract().isMulti()) {
            List<Object> objects = (List) process;
            for (int i = 0; i < objects.size(); i++) {
                page.putField(String.valueOf(objects.hashCode() + i), objects.get(i));
            }
        } else {
            page.putField(String.valueOf(process.hashCode()), process);
        }
    }

    public static boolean isExtractLinks(String url, List<String> targetUrls) {
        boolean isExtract = true;
        for (String s : targetUrls) {
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                isExtract = false;
                break;
            }
        }
        return isExtract;
    }
}
