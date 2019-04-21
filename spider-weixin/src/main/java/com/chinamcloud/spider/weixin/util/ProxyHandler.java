package com.chinamcloud.spider.weixin.util;

import com.chinamcloud.spider.orm.redis.RedisPool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import redis.clients.jedis.Jedis;
import sun.net.www.protocol.http.HttpURLConnection;
import us.codecraft.webmagic.proxy.Proxy;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProxyHandler {

    private static Gson gson = new Gson();

    private static String proxyUrl = "http://piping.mogumiao.com/proxy/api/get_ip_al?appKey=84c02a292d6948188b6b82885b9376c3&count=1&expiryDate=0&format=1&newLine=2";

    public static List<Proxy> get() {
        Jedis jedis = RedisPool.getJedis();
        String list = jedis.get("proxy:list");
        List<Proxy> proxies = gson.fromJson(list, new TypeToken<List<Proxy>>(){}.getType());
        jedis.close();
        return proxies;
    }

    public static List<Proxy> getNewProxy() {
        try {
            URL url = new URL(proxyUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            int code = urlConnection.getResponseCode();
            if (code == 200) {
                String result = new String(IOUtils.toByteArray(urlConnection.getInputStream()));
                System.out.println(result);
                Map map = gson.fromJson(result, Map.class);
                if (map.get("code").equals("0")) {
                    List<Proxy> proxies = new LinkedList<>();
                    List<Map<String, String>> mapList = (List<Map<String, String>>) map.get("msg");
                    for (Map<String, String> stringMap : mapList) {
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
}
