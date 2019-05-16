package com.chinamcloud.spider.sina.proxy;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.utils.ProxyHandler;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class SinaProxy implements ProxyProvider {

    private volatile Proxy proxy;

    private AtomicInteger integer;

    public SinaProxy() {
        this.integer = new AtomicInteger();
    }

    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {

    }

    @Override
    public Proxy getProxy(Request request) {
        synchronized (Proxy.class) {
            int i = integer.get();
            if (proxy == null || i > 5) {
                List<Proxy> proxies = ProxyHandler.getNewProxy();
                if (proxies != null && proxies.size() != 0) proxy = proxies.get(0);
                while (!integer.compareAndSet(i, 0)) {
                    i = integer.get();
                }
            }
            integer.incrementAndGet();
            return proxy;
        }
    }

    @Override
    public void destroy(Proxy proxy) {
        this.proxy = null;
    }
}
