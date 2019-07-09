package cn.stark.spider.download.proxy;

import cn.stark.spider.common.Page;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SingleProxyProvider implements ProxyProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String targetUrl = "http://mvip.piping.mogumiao.com/proxy/api/get_ip_al?appKey=84c02a292d6948188b6b82885b9376c3&count=1&expiryDate=0&format=1&newLine=2";

    private Proxy proxy;

    private Lock lock = new ReentrantLock();

    private int count = 0;

    @Override
    public void returnProxy(Proxy proxy, Page page) {
        lock.lock();
        count++;
        if (count > 100) {
            this.proxy = null;
        }
        if (!page.isDownloadSuccess()) {
            this.proxy = null;
        }
        lock.unlock();
    }

    @Override
    public Proxy getProxy() {
        lock.lock();
        if (proxy == null) {
            proxy = getNewProxy();
        }
        lock.unlock();
        return proxy;
    }



    private Proxy getNewProxy() {
        try {
            URL url = new URL(targetUrl);
            // 设置通过代理访问目标页面
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置协议头 (下面的必带，其他的可以自定义，根据自己的需求决定)
            // 解析返回数据
            byte[] response = readStream(connection.getInputStream());
            String r = new String(response);
            ProxyResult proxyResult = JSON.parseObject(r, ProxyResult.class);
            return proxyResult.get();
        }
        catch (Exception e) {
            logger.error("获取代理IP失败");
        }
        return null;
    }

    private byte[] readStream(InputStream inStream) throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }
}
