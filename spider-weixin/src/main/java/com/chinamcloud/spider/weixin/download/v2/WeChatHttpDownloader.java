package com.chinamcloud.spider.weixin.download.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinamcloud.spider.weixin.util.IdentifyImageUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.downloader.HttpClientGenerator;
import us.codecraft.webmagic.downloader.HttpClientRequestContext;
import us.codecraft.webmagic.downloader.HttpUriRequestConverter;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.CharsetUtils;
import us.codecraft.webmagic.utils.HttpClientUtils;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The http downloader based on HttpClient.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public class WeChatHttpDownloader extends AbstractDownloader {

    private String sougou_codeUrl = "http://weixin.sogou.com/antispider/util/seccode.php?tc=";

    private String wechat_codeUrl = "https://mp.weixin.qq.com/mp/verifycode?cert=";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();

    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

    private HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();
    
    private ProxyProvider proxyProvider = new WeChatProxy();

    private boolean responseHeader = true;

    public void setHttpUriRequestConverter(HttpUriRequestConverter httpUriRequestConSSitteverter) {
        this.httpUriRequestConverter = httpUriRequestConverter;
    }

    public void setProxyProvider(ProxyProvider proxyProvider) {
        this.proxyProvider = proxyProvider;
    }

    private CloseableHttpClient getHttpClient(Request request) {
        String type = request.getExtra("UnlockType").toString();
        CloseableHttpClient httpClient = httpClients.get(type);
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(type);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(Site.me());
                    httpClients.put(type, httpClient);
                }
            }
        }
        return httpClient;
    }

    @Override
    public Page download(Request request, Task task) {
        CloseableHttpClient httpClient = getHttpClient(request);
        Proxy proxy = proxyProvider.getProxy(request);
        Page page = execute(request, task, proxy, httpClient);
        if (page.getRawText() == null) {
            logger.error("downloading page error {}  rawText null", request.getUrl());
            page.setDownloadSuccess(false);
            return page;
        }
        if (page.getRawText().contains("请输入验证码")) {
            logger.warn("downloading page need verify {} ", request.getUrl());
            verify(page, httpClient, proxy);
            page.setDownloadSuccess(false);
        }
        return page;
    }


    private Page execute(Request request, Task task, Proxy proxy, CloseableHttpClient httpClient) {
        if (task == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }
        CloseableHttpResponse httpResponse = null;
        HttpClientRequestContext requestContext;
        if (proxy.getContext() == null) {
            requestContext = httpUriRequestConverter.convert(request, task.getSite(), proxy);
            proxy.setContext(requestContext.getHttpClientContext());
        } else {
            requestContext = new HttpClientRequestContext();
            requestContext.setHttpClientContext(proxy.getContext());
            requestContext.setHttpUriRequest(httpUriRequestConverter.convertHttpUriRequest(request, task.getSite(), proxy));
        }
        Page page = Page.fail();
        try {
            HttpClientContext context = requestContext.getHttpClientContext();
            //设置请求头，sougou反扒限制
            HttpUriRequest httpUriRequest = requestContext.getHttpUriRequest();
//            httpUriRequest.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
            httpUriRequest.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");
            httpResponse = httpClient.execute(httpUriRequest, context);
            page = handleResponse(request, request.getCharset() != null ? request.getCharset() : task.getSite().getCharset(), httpResponse, task);
            logger.info("downloading page success {} ", request.getUrl(), proxy.getHost());
            //update 设置cookie
            CookieStore cookieStore = context.getCookieStore();
            List<Cookie> cookies = cookieStore.getCookies();
            StringBuffer  cookieBuff = new StringBuffer();
            for (Cookie cookie : cookies) {
                cookieBuff.append(cookie.getName()).append("=");
                cookieBuff.append(cookie.getValue()).append(";");
            }
            if(cookieBuff.length()>0){
                page.setCookie(cookieBuff.toString().substring(0,cookieBuff.length()-1));
            }else{
                //set default cookie
                page.setCookie("ld=alllllllll2tHMdFlllllVhTE@tlllllLu0KUkllllylllll9klll5@@@@@@@@@@; SUID=52DF68DA7C20940A000000005CAD8018; CXID=506ED5056BB6226BED4C55792E3B9326; IPLOC=CN5101; LCLKINT=215172; usid=LKxyj8L_xHztU5mH; sct=22; SUV=006851C2DA68DF525CAD801864A9A777; LSTMV=288%2C23; wuid=AAHZPtrbJgAAAAqKFCh4HwAAkwA=; SNUID=FF73C576ADA92A64BE47748CADA9A927; JSESSIONID=aaaUO0bslsJwu4sURUPOw; PHPSESSID=p872nb42qhm2cfd2dvtt1e92m3; weixinIndexVisited=1; SUID=52DF68DA6E2F940A000000005CAD7997; ABTEST=3|1554872727|v1");
            }
            onSuccess(request);
            return page;
        } catch (SocketTimeoutException | ConnectTimeoutException | HttpHostConnectException | NoRouteToHostException e ) {
            proxyProvider.destroy(proxy);
            logger.warn("Connect Url: {} error", proxy.getHost(), e);
            page.setDownloadSuccess(false);
            onError(request);
            return page;
        } catch (IOException e) {
            logger.warn("download page {} error", request.getUrl(), e);
            page.setDownloadSuccess(false);
            onError(request);
            return page;
        } finally {
            if (httpResponse != null) {
                //ensure the connection is released back to pool
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
            proxyProvider.returnProxy(proxy, page, task);
        }
    }


    protected void verify(Page page, CloseableHttpClient httpClient, Proxy proxy) {
        page.setRawText("FAILED");
        if (page.getRequest().getExtra("UnlockType").equals("sogou")) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            String[] urls = page.getRequest().getUrl().split("weixin.sogou.com/");
            if (urls.length != 0) {
                try {
                    String urlQuote = urls[urls.length - 1];
                    String unlockUrl = "http://weixin.sogou.com/antispider/thank.php";
                    Date date = new Date();
                    int mi = (int)(Math.random() * date.getTime());
                    String url = sougou_codeUrl + mi;
                    HttpUriRequest imgR = httpUriRequestConverter.convertHttpUriRequest(new Request(url), proxy);
                    byte[] imgs = simpleDownload(imgR, proxy.getContext(), httpClient,urlQuote);
                    if (imgs == null) {
                        proxyProvider.destroy(proxy);
                        return;
                    }
                    String code = IdentifyImageUtil.verifyCode(imgs, "30600");
                    if (code == null) return; // 抛出异常
                    Map<String, Object> params = new HashMap<>();
                    params.put("c", code);
                    params.put("r", "%2F");
                    params.put("v", 5);
                    headers.put("Referer", "http://weixin.sogou.com/antispider/?from=%2f" + urlQuote);
                    Request request = new Request(unlockUrl);
                    request.setMethod("post");
                    request.setRequestBody(HttpRequestBody.form(params, "UTF-8"));
                    HttpUriRequest httpUriRequest = httpUriRequestConverter.convertHttpUriRequest(request, proxy);
                    byte[] bytes = simpleDownload(httpUriRequest, proxy.getContext(), httpClient);
                    if (bytes == null) {
                        proxyProvider.destroy(proxy);
                        return;
                    }
                    String result = new String(bytes,  "utf-8");
                    System.out.println(result);
                    if (result.contains("0")) {
                        JSONObject object = JSON.parseObject(result);
                        addCookie("SUV", getCookie("SUID", proxy), proxy);
                        addCookie("SNUID", object.getString("id"), proxy);
                        page.setRawText("OK");
                    } else {
                        page.setRawText("FAILED");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    page.setRawText("FAILED");
                }
            }
        } else {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            headers.put("Host", "mp.weixin.qq.com");
            headers.put("Referer", page.getRequest().getUrl());
            try {
                String unlockUrl = "https://mp.weixin.qq.com/mp/verifycode";
                Date date = new Date();
                int mi = (int)(Math.random() * date.getTime());
                String url = wechat_codeUrl + mi;
                byte[] imgs = simpleDownload(httpUriRequestConverter.convertHttpUriRequest(new Request(url), proxy), proxy.getContext(), httpClient);
                if (imgs == null) {
                    proxyProvider.destroy(proxy);
                    return;
                }
                String code = IdentifyImageUtil.verifyCode(imgs, "30400");
                if (code == null) return; // 抛出异常
                Map<String, Object> params = new HashMap<>();
                params.put("cert", new Date().getTime());
                params.put("input", code);
//                String param = "cert=" + new Date().getTime() + "&input=" + code;
                Request request = new Request(unlockUrl);
                request.setMethod("post");
                request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
                HttpUriRequest request1 = httpUriRequestConverter.convertHttpUriRequest(request, proxy);
                byte[] resutls = simpleDownload(request1, proxy.getContext(), httpClient);
                String result = new String(resutls, "utf-8");
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

    private byte[] simpleDownload(HttpUriRequest request, HttpClientContext context, CloseableHttpClient httpClient,String urlQuote) {
        CloseableHttpResponse httpResponse = null;
        try {
            request.setHeader("Referer", "http://weixin.sogou.com/antispider/?from=%2f" + urlQuote);
            httpResponse = httpClient.execute(request, context);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return IOUtils.toByteArray(httpResponse.getEntity().getContent());
            }
        } catch (IOException e) {
            logger.warn("download simple page {} error", e);
        } finally {
            if (httpResponse != null) {
                //ensure the connection is released back to pool
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
        }
        return null;
    }

    private byte[] simpleDownload(HttpUriRequest request, HttpClientContext context, CloseableHttpClient httpClient) {
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(request, context);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return IOUtils.toByteArray(httpResponse.getEntity().getContent());
            }
        } catch (IOException e) {
            logger.warn("download simple page {} error", e);
        } finally {
            if (httpResponse != null) {
                //ensure the connection is released back to pool
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
        }
        return null;
    }

    private byte[] getImg(String url, CloseableHttpClient httpClient, Proxy proxy) throws  IOException {
        Date date = new Date();
        int mi = (int)(Math.random() * date.getTime());
        url = url + mi;
        Request request = new Request(url);
        CloseableHttpResponse response = httpClient.execute(httpUriRequestConverter.convertHttpUriRequest(request, proxy), proxy.getContext());
        if (response.getStatusLine().getStatusCode() == 502) return null;
        byte[] imgs;
        try {
            InputStream stream = response.getEntity().getContent();
            imgs = IOUtils.toByteArray(stream);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consumeQuietly(response.getEntity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
//        FileImageOutputStream imageOutput = new FileImageOutputStream(new File("C:\\Users\\Jhon\\Pictures\\test2.jpg"));//打开输入流
//        imageOutput.write(imgs, 0, imgs.length);//将byte写入硬盘
//        imageOutput.close();
        return imgs;
    }

    @Override
    public void setThread(int thread) {
        httpClientGenerator.setPoolSize(thread);
    }

    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws IOException {
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
        if (!request.isBinaryContent()){
            if (charset == null) {
                charset = getHtmlCharset(contentType, bytes);
            }
            page.setCharset(charset);
            page.setRawText(new String(bytes, charset));
        }
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        page.setDownloadSuccess(true);
        if (responseHeader) {
            page.setHeaders(HttpClientUtils.convertHeaders(httpResponse.getAllHeaders()));
        }
        return page;
    }

    private String getHtmlCharset(String contentType, byte[] contentBytes) throws IOException {
        String charset = CharsetUtils.detectCharset(contentType, contentBytes);
        if (charset == null) {
            charset = Charset.defaultCharset().name();
            logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset());
        }
        return charset;
    }

    private String getCookie(String name, Proxy proxy) {
        Map<String, String> map = new HashMap<>();
        List<Cookie> cookies = proxy.getContext().getCookieStore().getCookies();
        for (Cookie c :
                cookies) {
            if (c.getName().equals(name)) return c.getValue();
        }
        return null;
    }

    private void addCookie(String name, String value, Proxy proxy) {
        Cookie cookie = new BasicClientCookie(name, value);
        ((BasicClientCookie) cookie).setDomain("weixin.sogou.com");
        ((BasicClientCookie) cookie).setPath("/");
        proxy.getContext().getCookieStore().addCookie(cookie);
    }
}
