package com.chinamcloud.spider.search.util;

public enum Site {
    BAI_DU("http://news.baidu.com/ns?word={kw}&tn=news&from=news&cl=2&rn=20&ct=1",
            "baidu",
            "utf-8",
            "百度_新闻搜索",
            517747192),
    SO_GOU("https://news.sogou.com/news?query={kw}",
            "so_gou",
            "GBK",
            "搜狗_新闻搜索",
            446664811),
    B_TIME("https://pc.api.btime.com/btimeweb/getSearchData?q={kw}&type=all&channel=search&refresh=1&req_count=1&refresh_type=2&pid=3",
            "b_time",
            "utf-8",
            "北京时间_新闻搜索",
            1411526435),
    SINA_NEWS("https://search.sina.com.cn/?q={kw}&range=all&c=news&sort=time",
            "sina_news",
            null,
            "新浪_新闻搜索",
            511351670);

    Site(String url, String type, String charset, String tbNickName, int sourceId) {
        this.url = url;
        this.type = type;
        this.charset = charset;
        this.tbNickName = tbNickName;
        this.sourceId = sourceId;
    }

    private String url;

    private String type;

    private String charset;

    private String tbNickName;

    private int sourceId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getTbNickName() {
        return tbNickName;
    }

    public void setTbNickName(String tbNickName) {
        this.tbNickName = tbNickName;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
