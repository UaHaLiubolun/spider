package cn.stark.spider.common.spider.scheduler;


import cn.stark.spider.common.spider.Scheduler;
import org.redisson.api.RDeque;
import org.redisson.api.RedissonClient;

public class RedisScheduler<E> implements Scheduler<E> {

    private RDeque<E> deque;

    public RedisScheduler(String name, RedissonClient redissonClient) {
        deque = redissonClient.getDeque(name);
    }


    @Override
    public void push(E request) {
        deque.push(request);
    }

    @Override
    public E poll() {
        return deque.poll();
    }
}
