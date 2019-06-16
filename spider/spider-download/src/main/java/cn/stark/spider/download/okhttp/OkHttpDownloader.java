package cn.stark.spider.download.okhttp;

import cn.stark.spider.common.Page;
import cn.stark.spider.common.Request;
import cn.stark.spider.common.spider.Downloader;
import cn.stark.spider.common.spider.Scheduler;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpDownloader implements Downloader {

    private OkHttpClient okHttpClient = new OkHttpClient();

    private Scheduler<Page> scheduler;

    public OkHttpDownloader(Scheduler<Page> scheduler) {
        this.scheduler = scheduler;
    }

    public OkHttpDownloader() {
    }

    @Override
    public Page download(Request request) {
        okhttp3.Request r = build(request);
        Call call = okHttpClient.newCall(r);
        Page page = new Page();
        try {
            Response response = call.execute();
            page = PageHandler.handleResponse(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public void asyncDownload(Request request) {
        okhttp3.Request r = build(request);
        Call call = okHttpClient.newCall(r);
        call.enqueue(new DownloadCallBack(request, scheduler));
    }

    private okhttp3.Request build(Request request) {
        return RequestBuilder.build(request);
    }



}
