package com.chinamcloud.spider.weixin.util;


import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {

    private String sessionId;


    public byte[] img(String url) {
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);//允许写出
            connection.setDoInput(true);//允许读入
            connection.setUseCaches(true);//不使用缓存
            connection.connect();//连接
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                String cookieValue = connection.getHeaderField("Set-Cookie");
//                sessionId = cookieValue.substring(0, cookieValue.indexOf(";"));
                sessionId = cookieValue;
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                return bytes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String post(String url, String data, Map<String, String> headers) {
        try {
            URL urlU = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlU.openConnection();
            connection.setRequestMethod("POST");//设置请求方式为POST
            connection.setDoOutput(true);//允许写出
            connection.setDoInput(true);//允许读入
            connection.setUseCaches(true);//不使用缓存
            connection.setRequestProperty("Cookie", sessionId);

            for (Map.Entry<String, String> e:
                 headers.entrySet()) {
                connection.setRequestProperty(e.getKey(), e.getValue());
            }
            connection.connect();//连接
            String body = data;
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            writer.write(body);
            writer.close();

            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                String str = new String(bytes);
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String executePost(String url, String queryParams) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).build();
        httpPost.setConfig(requestConfig);
        StringEntity entity = new StringEntity(queryParams, "UTF-8");
        entity.setContentType("text/json");
        httpPost.setEntity(entity);
        String result = "";

        try {
            response = httpClient.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity2 = response.getEntity();
                result = EntityUtils.toString(entity2);
                EntityUtils.consume(entity2);
            } else {
                System.out.println(url);
            }
        } catch (IOException var17) {
            var17.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException var16) {
                var16.printStackTrace();
            }

        }

        return result;
    }
}
