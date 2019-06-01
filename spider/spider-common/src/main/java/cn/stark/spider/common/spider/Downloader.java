package cn.stark.spider.common.spider;

import cn.stark.spider.common.Page;
import cn.stark.spider.common.Request;

public interface Downloader {


    /**
     * 下载
     * @param request 请求
     * @return 页面
     */
    Page download(Request request);
}
