package us.codecraft.webmagic.proxy;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;

/**
 * Proxy provider. <br>
 *     
 * @since 0.7.0
 */
public interface ProxyProvider {

    /**
     *
     * Return proxy to Provider when complete a download.
     * @param proxy the proxy config contains host,port and identify info
     * @param page the download result
     * @param task the download task
     */
    void returnProxy(Proxy proxy, Page page, Task task);

    /**
     * Get a proxy for task by some strategy.
     * @param request the download request
     * @return proxy 
     */
    Proxy getProxy(Request request);


    /**
     * 销毁失效代理
     * @param proxy
     */
    void destroy(Proxy proxy);
    
}
