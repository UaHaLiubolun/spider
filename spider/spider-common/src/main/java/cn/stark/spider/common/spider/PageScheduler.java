package cn.stark.spider.common.spider;


import cn.stark.spider.common.Page;

public interface PageScheduler {

    void push(Page page);


    Page poll();
}
