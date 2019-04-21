package com.chinamcloud.spider.weixin.download;


import com.chinamcloud.spider.weixin.util.ProxyHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientRequestContext;
import us.codecraft.webmagic.downloader.HttpUriRequestConverter;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.selector.PlainText;

import javax.imageio.stream.FileImageOutputStream;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class AbstractDownload {


    private CloseableHttpClient httpClient = null;
    HttpClientContext context = null;
    CookieStore cookieStore = null;
    RequestConfig requestConfig = null;
    PoolingHttpClientConnectionManager connManager;
    private List<Proxy> proxies;
    SocketConfig socketConfig;

    private Logger logger = LoggerFactory.getLogger(getClass());



    public Page get(Request request, Task task) {
        Page page = download(request, task);
        if (needVerify(page)) {
            verify(page);
            page = download(request, task);
            return page;
        } else {
            return page;
        }
    }

    private CloseableHttpClient getClient(Proxy proxy) {
        cookieStore.clear();
        HttpClientBuilder builder = HttpClients.custom()
                .setConnectionManager(connManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCookieStore(cookieStore)
                .setDefaultSocketConfig(socketConfig)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
        if (proxy != null) {
            builder.setProxy(new HttpHost(proxy.getHost(), proxy.getPort()));
        }
        return builder.build();
    }


    private Page download(Request request, Task task) {
        if (task == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }
        CloseableHttpResponse httpResponse = null;
        HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();
        HttpClientRequestContext requestContext;
        request.setUrl(request.getUrl().replace(" ", ""));
        Proxy proxy = getProxy();
        if (proxy != null && proxy.getSuccess() > 5 && !request.getExtra("ProcessType").equals("content")) {
            proxies.remove(proxy);
            proxy = getProxy();
        }
        requestContext = httpUriRequestConverter.convert(request, task.getSite(), proxy);
        Page page = Page.fail();
        httpClient = getClient(proxy);
        try {
            httpResponse = httpClient.execute(requestContext.getHttpUriRequest(), requestContext.getHttpClientContext());
            page = handleResponse(request, httpResponse);
            logger.info("download: " + request.getUrl() + " proxy: " + proxy.getHost());
            proxy.success();
            return page;
        } catch (SocketTimeoutException | ConnectTimeoutException | HttpHostConnectException | NoRouteToHostException e ) {
            proxies.remove(proxy);
            e.printStackTrace();
            request.setExtractLinks(true);
            page.addTargetRequest(request);
            return page;
        } catch (IOException e) {
            e.printStackTrace();
            return page;
        } finally {
            if (httpResponse != null) {
                try {
                    EntityUtils.consumeQuietly(httpResponse.getEntity());
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private Page handleResponse(Request request, HttpResponse httpResponse) throws IOException {
        byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
        String contentType = httpResponse.getEntity().getContentType() == null ? "" : httpResponse.getEntity().getContentType().getValue();
        Page page = new Page();
        if (contentType != null) {
            String[] contentTypes = contentType.split(";");
            if (contentTypes.length >= 1) {
                page.setContentType(contentTypes[0]);
            }
        }
        page.setBytes(bytes);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        page.setDownloadSuccess(true);
        page.setRawText(new String(bytes, "utf-8"));
        return page;
    }





    private boolean needVerify(Page page) {
        if (page.getRawText() == null) return false;
        return page.getRawText().contains("请输入验证码");
    }

    protected abstract void verify(Page page);

    private Proxy getProxy() {
        if (proxies == null || proxies.size() == 0) {
            proxies = ProxyHandler.getNewProxy();
        }
        if (proxies.size() == 0) {
            return null;
        }
        return proxies.get(0);
    }


    byte[] getImg(String url) throws ClientProtocolException, IOException {
        Date date = new Date();
        int mi = (int)(Math.random() * date.getTime());
        url = url + mi;
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpget, context);
        if (response.getStatusLine().getStatusCode() == 502) return null;
        byte[] imgs;
        try {
            InputStream stream = response.getEntity().getContent();
            imgs = IOUtils.toByteArray(stream);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consumeQuietly(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        FileImageOutputStream imageOutput = new FileImageOutputStream(new File("C:\\Users\\Jhon\\Pictures\\test2.jpg"));//打开输入流
        imageOutput.write(imgs, 0, imgs.length);//将byte写入硬盘
        imageOutput.close();
        return imgs;
    }

    void addCookie(String name, String value) {
        Cookie cookie = new BasicClientCookie(name, value);
        ((BasicClientCookie) cookie).setDomain("weixin.sogou.com");
        ((BasicClientCookie) cookie).setPath("/");
        cookieStore.addCookie(cookie);
    }

    Cookie getCookie(String name) {
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }


    String post(String url, String parameters, Request request, Map<String, String> headers)
            throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = toNameValuePairList(parameters);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        for (Map.Entry<String, String> m:
                headers.entrySet()) {
            httpPost.setHeader(m.getKey(), m.getValue());
        }
        CloseableHttpResponse response = httpClient.execute(httpPost, context);
        try {
            byte[] bytes = IOUtils.toByteArray(response.getEntity().getContent());
            return new String(bytes, "utf-8");
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consumeQuietly(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<NameValuePair> toNameValuePairList(String parameters) {
        List<NameValuePair> nvps = new ArrayList<>();
        String[] paramList = parameters.split("&");
        for (String parm : paramList) {
            int index = -1;
            for (int i = 0; i < parm.length(); i++) {
                index = parm.indexOf("=");
                break;
            }
            String key = parm.substring(0, index);
            String value = parm.substring(++index, parm.length());
            nvps.add(new BasicNameValuePair(key, value));
        }
        System.out.println(nvps.toString());
        return nvps;
    }

    SSLConnectionSocketFactory buildSSLConnectionSocketFactory() {
        try {
            return new SSLConnectionSocketFactory(createIgnoreVerifySSL(), new String[]{"SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"},
                    null,
                    new DefaultHostnameVerifier()); // 优先绕过安全证书
        } catch (KeyManagementException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        return SSLConnectionSocketFactory.getSocketFactory();
    }

    SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

        };

        SSLContext sc = SSLContext.getInstance("SSLv3");
        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }


}
