package com.chinamcloud.spider.weixin.download;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;

public class WeChatDownloader implements Downloader {

    private AbstractDownload souGouDownloader = SoGouDownloader.getInstance();

    private AbstractDownload weChatDetailDownloader = WeChatDetailDownloader.getInstance();


    @Override
    public Page download(Request request, Task task) {
        if (request.getExtra("UnlockType").equals("weixin")) {
            Page page = weChatDetailDownloader.get(request, task);
            return page;
        } else {
            Page page = souGouDownloader.get(request, task);
            return page;
        }
    }

    @Override
    public void setThread(int threadNum) {

    }
}
