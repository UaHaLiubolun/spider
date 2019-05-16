package com.chinamcloud.spider.weixin.download;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinamcloud.spider.orm.redis.RedisPool;
import com.chinamcloud.spider.weixin.util.IdentifyImageUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.proxy.Proxy;

import java.net.URI;
import java.util.*;

public class SoGouDownloader extends AbstractDownload {


    private String codeUrl = "http://weixin.sogou.com/antispider/util/seccode.php?tc=";

    private SoGouDownloader() {}

    private static SoGouDownloader soGouDownloader = new SoGouDownloader();

    public static SoGouDownloader getInstance() {
        return soGouDownloader;
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
        requestConfig = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(60000)
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
        String[] urls = page.getRequest().getUrl().split("weixin.sogou.com/");
        if (urls.length != 0) {
            try {
                String urlQuote = urls[urls.length - 1];
                String unlockUrl = "http://weixin.sogou.com/antispider/thank.php";
                String code = IdentifyImageUtil.verifyCode(getImg(codeUrl), "30600");
                if (code == null) return; // 抛出异常
                String param = "c=" + code + "&r=%2F&v=5";
                headers.put("Referer", "http://weixin.sogou.com/antispider/?from=%2f" + urlQuote);
                String result = post(unlockUrl, param, page.getRequest(), headers);
                System.out.println(result);
                if (result.contains("0")) {
                    JSONObject object = JSON.parseObject(result);
                    addCookie("SUV", getCookie("SUID").getValue());
                    addCookie("SNUID", object.getString("id"));
                    page.setRawText("OK");
                } else {
                    page.setRawText("FAILED");
                }
            } catch (Exception e) {
                e.printStackTrace();
                page.setRawText("FAILED");
            }
        }
    }


    private void clearCookie(Page page){
        cookieStore.clear();
        String suv = String.valueOf(1000l * new Date().getTime() + Math.round(1000 * Math.random()));
        Cookie cookie = new BasicClientCookie("SUV", DigestUtils.md5Hex(suv).toUpperCase());
        ((BasicClientCookie) cookie).setDomain("weixin.sogou.com");
        ((BasicClientCookie) cookie).setPath("/");
        cookieStore.addCookie(cookie);
    }
}
