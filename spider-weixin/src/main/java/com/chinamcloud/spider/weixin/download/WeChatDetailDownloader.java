package com.chinamcloud.spider.weixin.download;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinamcloud.spider.orm.redis.RedisPool;
import com.chinamcloud.spider.weixin.util.IdentifyImageUtil;
import com.chinamcloud.spider.weixin.util.ProxyHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.proxy.Proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeChatDetailDownloader extends AbstractDownload {

    private String codeUrl = "https://mp.weixin.qq.com/mp/verifycode?cert=";

    private WeChatDetailDownloader() {}

    private static WeChatDetailDownloader weChatDetailDownloader = new WeChatDetailDownloader();

    public static WeChatDetailDownloader getInstance() {
        return weChatDetailDownloader;
    }

    {
        init();
    }

    private void init() {
        context = HttpClientContext.create();
        cookieStore = new BasicCookieStore();
        socketConfig = SocketConfig.custom()
                .setSoTimeout(20000)
                .setTcpNoDelay(true)
                .setSoKeepAlive(true).build();
        // 配置超时时间（连接服务端超时1秒，请求数据返回超时2秒）
        requestConfig = RequestConfig.custom().setConnectTimeout(50000).setSocketTimeout(50000)
                .setConnectionRequestTimeout(60000).build();

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", buildSSLConnectionSocketFactory())
                .build();
        connManager = new PoolingHttpClientConnectionManager(reg);
    }

    @Override
    protected void verify(Page page) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Host", "mp.weixin.qq.com");
        headers.put("Referer", page.getRequest().getUrl());
        try {
            String unlockUrl = "https://mp.weixin.qq.com/mp/verifycode";
            String code = IdentifyImageUtil.verifyCode(getImg(codeUrl), "30400");
            String param = "cert=" + new Date().getTime() + "&input=" + code;
            String result = post(unlockUrl, param, page.getRequest(), headers);
            JSONObject object = JSON.parseObject(result);
            if (object.getInteger("ret") == 0) {
                page.setRawText("OK");
            } else {
                page.setRawText("FAILED");
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            page.setRawText("FAILED");
        }
    }

}
