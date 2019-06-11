package cn.stark.spider.download;

import cn.stark.spider.common.Request;
import cn.stark.spider.common.spider.Scheduler;
import cn.stark.spider.common.spider.scheduler.RedisScheduler;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

public class AddUrl {

    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create();
        Scheduler<Request> requestScheduler = new RedisScheduler<>("request", redissonClient);
        requestScheduler.push(new Request("http://movie.douban.com/j/new_search_subjects?sort=S&range=0,10&tags=0"));
//        Request request = requestScheduler.poll();
//        System.out.println(request.getUrl());
        redissonClient.shutdown();
    }
}
