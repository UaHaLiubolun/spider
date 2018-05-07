package spider.downloader;


import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import spider.Request;
import spider.Task;
import spider.utils.UrlUtils;

import java.util.Map;

public class HttpRequestConvert {

    public HttpClientRequestContext convert(Task task) {
        HttpClientRequestContext httpClientRequestContext = new HttpClientRequestContext();
        httpClientRequestContext.setHttpUriRequest(convertHttpUriRequest(task));
        httpClientRequestContext.setHttpClientContext(new HttpClientContext());
        return httpClientRequestContext;
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
