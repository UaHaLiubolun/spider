package cn.stark.spider.download;

import cn.stark.spider.common.Request;
import cn.stark.spider.common.bean.DouBanRequest;
import cn.stark.spider.common.bean.DouBanRequestBuilder;
import cn.stark.spider.common.spider.Scheduler;
import cn.stark.spider.common.spider.scheduler.RedisScheduler;
import cn.stark.spider.common.utils.RedisConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

import java.util.List;

public class AddUrl {

    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create(RedisConfig.getConfig());
        Scheduler<Request> requestScheduler = new RedisScheduler<>("request", redissonClient);
        DouBanRequest request = DouBanRequestBuilder.douBanRequestBuilder().withTags("电影").build();
        List<Request> requests = request.generateRequest(200);
        requests.forEach(r -> {
            try {
                Thread.sleep(1000);
                requestScheduler.push(r);
            } catch (Exception e) {

            }
        });
        redissonClient.shutdown();
    }

}
