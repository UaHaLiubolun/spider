package com.chinamcloud.spider.any.proxy.provider;

import com.chinamcloud.spider.weixin.util.ProxyHandler;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AnyProxyProvider implements ProxyProvider {

    private volatile Proxy sProxy;

    private AtomicInteger sInteger;

    public AnyProxyProvider() {
        sInteger = new AtomicInteger();
    }

    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {

    }

    @Override
    public synchronized Proxy getProxy(Request request) {
        int i = sInteger.incrementAndGet();
            if (i > 50 || sProxy == null) {
                List<Proxy> list = ProxyHandler.getNewProxy();
                if (list.size() == 0) return null;
                sProxy = list.get(0);
                while (!sInteger.compareAndSet(i, 0)) {
                    i = sInteger.get();
                }
            }
        return sProxy;
    }

    @Override
    public synchronized void destroy(Proxy proxy) {
        this.sProxy = null;
    }
}
