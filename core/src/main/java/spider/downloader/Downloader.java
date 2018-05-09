package spider.downloader;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import spider.Page;
import spider.Request;
import spider.Site;
import spider.Task;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Downloader {

    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();

    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

    private HttpRequestConvert httpUriRequestConverter = new HttpRequestConvert();

    private CloseableHttpClient getHttpClient(Site site) {
        if (site == null) return httpClientGenerator.getClient(null);
        String domain = site.getDomain();
        CloseableHttpClient httpClient = httpClients.get(site.getDomain());
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }

    public Page downloader(Task task) {
        if (task == null || task.getRequest() == null || task.getSite() == null) {
            throw new NullPointerException("task or site can not be null");
        }
        CloseableHttpResponse httpResponse = null;
        Page page = new Page();
        CloseableHttpClient httpClient = getHttpClient(task.getSite());
        HttpClientRequestContext requestContext = httpUriRequestConverter.convert(task);
        System.out.println(task.getRequest().getUrl());
        try {
             httpResponse = httpClient.execute(requestContext.getHttpUriRequest(),
                    requestContext.getHttpClientContext());
             page = handleResponse(task, httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null)
                EntityUtils.consumeQuietly(httpResponse.getEntity());
        }
        return page;
    }

    private Page handleResponse(Task task, HttpResponse httpResponse) throws IOException {
        byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
        Page page = new Page();
        page.setRawText(new String(bytes, task.getSite().getCharset()));
        page.setRequest(task.getRequest());
        return page;
    }

    public void setThread(int size) {
        httpClientGenerator.setPoolSize(size);
    }
}
