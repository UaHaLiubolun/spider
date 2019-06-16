package cn.stark.spider.download.proxy;

import cn.stark.spider.common.Page;


/**
 * Proxy provider. <br>
 *     
 * @since 0.7.0
 */
public interface ProxyProvider {


    void returnProxy(Proxy proxy, Page page);


    Proxy getProxy();
    
}
