package cn.stark.spider.common.spider.scheduler;


import cn.stark.spider.common.spider.Scheduler;

public class RedisScheduler<E> implements Scheduler<E> {

    public RedisScheduler() {

    }

    @Override
    public void push(E request) {

    }

    @Override
    public E poll() {
        return null;
    }
}
