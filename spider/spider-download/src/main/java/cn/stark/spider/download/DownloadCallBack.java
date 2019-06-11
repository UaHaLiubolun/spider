package cn.stark.spider.download;

import cn.stark.spider.common.Page;
import cn.stark.spider.common.Request;
import cn.stark.spider.common.spider.Scheduler;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

public class DownloadCallBack implements Callback {

    private Request request;

    private Scheduler<Page> scheduler;

    public DownloadCallBack(Request request, Scheduler scheduler) {
        this.request = request;
        this.scheduler = scheduler;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Page page = PageHandler.handleResponse(request, response);
        scheduler.push(page);
    }

}
