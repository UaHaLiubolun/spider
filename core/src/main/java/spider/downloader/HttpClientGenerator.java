package spider.downloader;

import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import spider.Site;


public class HttpClientGenerator {

    private PoolingHttpClientConnectionManager connectionManager;

    public HttpClientGenerator() {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(50);
    }

    public void setPoolSize(int poolSize) {
        connectionManager.setMaxTotal(poolSize);
    }

    public CloseableHttpClient getClient(Site site) {
        return generateClient(site);
    }

    private CloseableHttpClient generateClient(Site site) {
        HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder.setConnectionManager(connectionManager);
        if (site.getUserAgent() != null)
            clientBuilder.setUserAgent(site.getUserAgent());
        else clientBuilder.setUserAgent("");

        SocketConfig.Builder socketConfigBuilder = SocketConfig.custom();
        socketConfigBuilder.setSoKeepAlive(true).setTcpNoDelay(true);
        socketConfigBuilder.setSoTimeout(site.getTimeOut());
        SocketConfig socketConfig = socketConfigBuilder.build();
        clientBuilder.setDefaultSocketConfig(socketConfig);
        return clientBuilder.build();
    }
}
