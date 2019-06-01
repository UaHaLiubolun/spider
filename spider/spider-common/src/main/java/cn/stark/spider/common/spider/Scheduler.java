package cn.stark.spider.common.spider;

import cn.stark.spider.common.Request;

public interface Scheduler {

    void push(Request request);


    Request poll();
}
