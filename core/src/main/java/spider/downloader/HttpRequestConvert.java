package spider.downloader;


import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import spider.Request;
import spider.Task;
import spider.utils.MySQLUtil;
import spider.utils.UrlUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class HttpRequestConvert {

    public HttpClientRequestContext convert(Task task) {
        HttpClientRequestContext httpClientRequestContext = new HttpClientRequestContext();
        httpClientRequestContext.setHttpUriRequest(convertHttpUriRequest(task));
        httpClientRequestContext.setHttpClientContext(convertHttpClientContext(task));
        return httpClientRequestContext;
    }


    private HttpClientContext convertHttpClientContext(Task task) {
        HttpClientContext httpContext = new HttpClientContext();
        // 临时设置一个代理

        if (task.getRequest().getCookies() != null && !task.getRequest().getCookies().isEmpty()) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Map.Entry<String, String> cookieEntry : task.getRequest().getCookies().entrySet()) {
                BasicClientCookie cookie1 = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
                cookie1.setDomain(UrlUtils.removePort(UrlUtils.getDomain(task.getRequest().getUrl())));
                cookieStore.addCookie(cookie1);
            }
            httpContext.setCookieStore(cookieStore);
        }
        return httpContext;
    }



    private HttpUriRequest convertHttpUriRequest(Task task) {
        RequestBuilder requestBuilder = selectRequestMethod(task.getRequest()).
                setUri(UrlUtils.fixIllegalCharacterInUrl(task.getRequest().getUrl()));
        if (task.getRequest().getHeaders() != null) {
            for (Map.Entry<String, String> headerEntry : task.getSite().getDefaultHeaders().entrySet()) {
                requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (task.getSite() != null) {
            requestConfigBuilder.setConnectionRequestTimeout(task.getSite().getTimeOut())
                    .setSocketTimeout(task.getSite().getTimeOut())
                    .setConnectTimeout(task.getSite().getTimeOut())
                    .setCookieSpec(CookieSpecs.STANDARD);
        }

        if (task.getSite().isProxy()) {
            String proxy = MySQLUtil.getProxy();
            if (!proxy.equals("")) {
                String[] tmp = proxy.split(":");
                HttpHost httpHost = new HttpHost(tmp[0], Integer.parseInt(tmp[1]), "http");
                requestConfigBuilder.setProxy(httpHost);
            }
        }

        requestBuilder.setConfig(requestConfigBuilder.build());
        HttpUriRequest httpUriRequest = requestBuilder.build();
        if (task.getRequest().getHeaders() != null && !task.getRequest().getHeaders().isEmpty()) {
            for (Map.Entry<String, String> header : task.getRequest().getHeaders().entrySet()) {
                httpUriRequest.addHeader(header.getKey(), header.getValue());
            }
        }
        return httpUriRequest;
    }

    private RequestBuilder selectRequestMethod(Request request) {
        String method = request.getMethod();
        if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
            //default get
            return RequestBuilder.get();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
            return addFormParams(RequestBuilder.post(),request);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
            return RequestBuilder.head();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
            return addFormParams(RequestBuilder.put(), request);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
            return RequestBuilder.delete();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
            return RequestBuilder.trace();
        }
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }

    private RequestBuilder addFormParams(RequestBuilder requestBuilder, Request request) {
        if (request.getRequestBody() != null) {
            ByteArrayEntity entity = new ByteArrayEntity(request.getRequestBody().getBody());
            entity.setContentType(request.getRequestBody().getContentType());
            requestBuilder.setEntity(entity);
        }
        return requestBuilder;
    }

}
