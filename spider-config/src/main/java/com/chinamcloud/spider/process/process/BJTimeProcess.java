package com.chinamcloud.spider.process.process;

import com.chinamcloud.spider.handle.DataConversionUtil;
import com.chinamcloud.spider.handle.ProcessUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.phantomJs.PhantomDownloader;
import us.codecraft.webmagic.selector.Html;

import java.util.*;

public class BJTimeProcess implements OwnProcess {

    private DataConversionUtil dcUtil = new DataConversionUtil();

    private PhantomDownloader httpClientDownloader = new PhantomDownloader();

    private Map<String, Object> map = new HashMap<>();

    private String content;

    private List<String> imgs = new LinkedList<>();

    private String nextUrl;

    private Html html;

    private String rqUrl;

    @Override
    public Object process(Page page) {
        rqUrl = page.getRequest().getUrl();
        html = page.getHtml();
        ProcessUtil.tranToMap(map, page);
        extract();
        isNext();
        while (nextUrl != null) {
            download();
            extract();
            isNext();
        }
        map.put("description", content);
        map.put("pics", imgs);
        return map;
    }

    private void extract() {
        if (nextUrl == null) {
            String subject = html.xpath("//h1[@id='title']/text()").get();
            content = html.xpath("//div[@class='content-text']/newsText()").get();
            String pubTime = html.xpath("//div[@class='content-info']//span[@class='col time']/text()").get();
            String parentSource = html.xpath("//div[@class='content-info']//span[@class='col cite']/text()").get();
            imgs.addAll(html.xpath("//div[@class='content-text']//img/@src").all());
            map.put("subject", subject);
            map.put("putTime", dcUtil.fmtDate(pubTime, "yyyy-MM-dd HH:mm:ss"));
            map.put("parentSource", parentSource);
        } else {
            content += html.xpath("//div[@class='content-text']/newsText()").get();
            imgs.addAll(html.xpath("//div[@class='content-text']//img/@src").all());
        }
    }

    private void isNext() {
        List<String> selectable = html.xpath("//div[@class='pagination']//a/@href").all();
        if (selectable.size() > 2) {
            String url = selectable.get(selectable.size() - 1);
            if (!url.equals(rqUrl + "?page=-1")) {
                nextUrl = url;
            } else {
                nextUrl = null;
            }
        } else {
            nextUrl = null;
        }
    }

    private void download() {
        html = httpClientDownloader.download(nextUrl);
    }
}
