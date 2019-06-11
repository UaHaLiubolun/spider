package cn.stark.spider.common.spider;


public interface Scheduler<E> {

    void push(E request);


    E poll();
}
