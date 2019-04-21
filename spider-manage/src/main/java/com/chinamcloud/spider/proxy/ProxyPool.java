package com.chinamcloud.spider.proxy;

import com.chinamcloud.spider.orm.redis.RedisPool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import redis.clients.jedis.Jedis;
import sun.net.www.protocol.http.HttpURLConnection;
import us.codecraft.webmagic.proxy.Proxy;

import javax.net.ssl.HttpsURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProxyPool implements Runnable {

    private Gson gson = new Gson();

    private String proxyUrl = "http://piping.mogumiao.com/proxy/api/get_ip_al?appKey=84c02a292d6948188b6b82885b9376c3&count=3&expiryDate=0&format=1&newLine=2";


    @Override
    public void run() {
        List<Proxy> proxies = getExistProxy();
        proxies = testProxy(proxies);
        while (proxies.size() <= 2) {
            List<Proxy> newProxy = getNewProxy();
            proxies.addAll(testProxy(newProxy));
        }
        setProxies(proxies);
    }

    private List<Proxy> getExistProxy() {
        Jedis jedis = RedisPool.getJedis();
        String list = jedis.get("proxy:list");
        if (list == null) return new ArrayList<>();
        System.out.println("exist: " + list);
        List<Proxy> proxies = gson.fromJson(list, new TypeToken<List<Proxy>>(){}.getType());
        jedis.close();
        return proxies;
    }


    private void setProxies(List<Proxy> proxies) {
        Jedis jedis = RedisPool.getJedis();
        jedis.set("proxy:list", gson.toJson(proxies));
        jedis.close();
    }

    private List<Proxy> getNewProxy() {
        try {
            URL url = new URL(proxyUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            int code = urlConnection.getResponseCode();
            if (code == 200) {
                String result = new String(IOUtils.toByteArray(urlConnection.getInputStream()));
                System.out.println("new: " + result);
                Map map = gson.fromJson(result, Map.class);
                if (map.get("code").equals("0")) {
                    List<Proxy> proxies = new LinkedList<>();
                    List<Map<String, String>> mapList = (List<Map<String, String>>)map.get("msg");
                    for (Map<String, String> stringMap:
                         mapList) {
                        Proxy proxy = new Proxy(stringMap.get("ip"), Integer.parseInt(stringMap.get("port")));
                        proxies.add(proxy);
                    }
                    return proxies;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<Proxy> testProxy(List<Proxy> proxies) {
        List<Proxy> newProxies = new LinkedList<>();
        for (Proxy p :
                proxies) {
            try {
                URL url = new URL("http://www.baidu.com/");
                InetSocketAddress inetSocketAddress = new InetSocketAddress(p.getHost(), p.getPort());
                java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, inetSocketAddress);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(proxy);
                urlConnection.setReadTimeout(1000);
                urlConnection.setConnectTimeout(1000);
                urlConnection.connect();
                int code = urlConnection.getResponseCode();
                if (code == 200) {
                    newProxies.add(p);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newProxies;
    }

    public static void main(String[] args) {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
        schedule.scheduleAtFixedRate(new ProxyPool(), 1, 30, TimeUnit.SECONDS);
    }


}
