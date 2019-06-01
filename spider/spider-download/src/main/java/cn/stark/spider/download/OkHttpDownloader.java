package cn.stark.spider.download;

import cn.stark.spider.common.Page;
import cn.stark.spider.common.Request;
import cn.stark.spider.common.spider.Downloader;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OkHttpDownloader implements Downloader {

    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public Page download(Request request) {
        okhttp3.Request r = build(request);
        Call call = okHttpClient.newCall(r);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            }
        });
        return null;
    }


    private okhttp3.Request build(Request request) {
        return RequestBuilder.build(request);
    }



}
