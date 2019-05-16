package cn.stark.spider.common;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Request implements Serializable {

    private static final long serialVersionUID = 2062192774891352043L;

    /**
     * Url
     */
    private String url;
    /**
     * 请求方式
     */
    private String method;
    /**
     * 编码
     */
    private String charset;
    /**
     * Process
     */
    private String process;
    /**
     * Cookies
     */
    private Map<String, String> cookies = new HashMap<>();
    /**
     * 请求头
     */
    private Map<String, String> headers = new HashMap<>();

}
