package us.codecraft.webmagic.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import sun.net.www.protocol.http.HttpURLConnection;
import us.codecraft.webmagic.proxy.Proxy;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProxyHandler {


    private static String proxyUrl = "http://piping.mogumiao.com/proxy/api/get_ip_al?appKey=84c02a292d6948188b6b82885b9376c3&count=1&expiryDate=0&format=1&newLine=2";

    public static List<Proxy> getNewProxy() {
        try {
            URL url = new URL(proxyUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            int code = urlConnection.getResponseCode();
            if (code == 200) {
                String result = new String(IOUtils.toByteArray(urlConnection.getInputStream()));
                Map map = JSON.parseObject(result, Map.class);
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
