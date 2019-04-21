package com.chinamcloud.spider.weixin.download.v2;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.utils.ProxyHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class WeChatProxy implements ProxyProvider {

    private volatile Proxy sProxy;

    private AtomicInteger sInteger;

    private volatile Proxy wProxy;

    private AtomicInteger wInteger;


    public WeChatProxy() {
        this.sInteger = new AtomicInteger();
        this.wInteger = new AtomicInteger();
    }

    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {

    }

    @Override
    public Proxy getProxy(Request request) {
        if (request.getExtra("UnlockType").equals("sogou")) {
            int i = sInteger.get();
            if (sProxy == null || i > 5) {
                sProxy = ProxyHandler.getNewProxy().get(0);
                sInteger.compareAndSet(i, 0);
            }
            sInteger.incrementAndGet();
            return sProxy;
        } else {
            int i = wInteger.get();
            if (wProxy == null || i > 15) {
                wProxy = ProxyHandler.getNewProxy().get(0);
                wInteger.compareAndSet(i, 0);
            }
            wInteger.incrementAndGet();
            return wProxy;
        }
    }


    @Override
    public void destroy(Proxy proxy) {
        if (proxy == wProxy) wProxy = null;
        else sProxy = null;
    }
}
