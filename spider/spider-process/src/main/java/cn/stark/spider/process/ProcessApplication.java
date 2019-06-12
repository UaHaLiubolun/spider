package cn.stark.spider.process;

import cn.stark.spider.common.spider.process.PageProcessStarter;
import cn.stark.spider.common.spider.scheduler.RedisScheduler;
import cn.stark.spider.common.utils.RedisConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

public class ProcessApplication {

    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create(RedisConfig.getConfig());
        PageProcessStarter pageProcessStarter = PageProcessStarter.create(new AutoProcess());
        pageProcessStarter.setPageScheduler(new RedisScheduler<>("page", redissonClient));
        pageProcessStarter.run();
    }
}
