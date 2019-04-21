package com.chinamcloud.spider.test;


import com.chinamcloud.spider.test.webCollector.ContentExtractor;
import com.chinamcloud.spider.test.webCollector.News;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;

public class TestUrl {

    public static void main(String[] args) throws Exception{
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        Html html = httpClientDownloader.download("https://www.toutiao.com/a6613228392231731716/");
        News news = ContentExtractor.getNewsByDoc(html.getDocument());
        System.out.println(news.getContent());
    }
}
