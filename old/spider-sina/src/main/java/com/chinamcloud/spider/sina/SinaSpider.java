package com.chinamcloud.spider.sina;

import com.chinamcloud.spider.sina.pipeline.SinaPipeline;
import com.chinamcloud.spider.sina.process.SinaProcess;
import com.chinamcloud.spider.sina.proxy.SinaProxy;
import com.chinamcloud.spider.sina.utils.RequestGenerate;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

public class SinaSpider {

    public static void main(String[] args) {
//        Downloader downloader = new HttpClientDownloader();
//        ((HttpClientDownloader) downloader).setProxyProvider(new SinaProxy());
        Spider spider = Spider.create(new SinaProcess()).thread(2).addPipeline(new SinaPipeline());

        Request request = RequestGenerate.mWeiBo( "4267903429656973");
        spider.addRequest(request);
//        Request rePost = RequestGenerate.rePost("4267903429656973");
//        Request comment = RequestGenerate.comment("4267903429656973", 1);
//        spider.addRequest(rePost, comment);
        spider.run();
    }
}
