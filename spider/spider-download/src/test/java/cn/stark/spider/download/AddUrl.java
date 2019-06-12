package cn.stark.spider.download;

import cn.stark.spider.common.Request;
import cn.stark.spider.common.spider.Scheduler;
import cn.stark.spider.common.spider.scheduler.RedisScheduler;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

public class AddUrl {

    private static String baseUrl = "http://movie.douban.com/j/new_search_subjects?range=0,10";

    private static String tag = "";

    /**
     * 排序
     * S 评分
     * T 标价
     * U 热门
     * R 最新
     */
    private static String sort = "";

    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create();
        Scheduler<Request> requestScheduler = new RedisScheduler<>("request", redissonClient);
        for (int i = 0; i < 1; i++) {
            requestScheduler.push(new Request(generateUrl(i * 20)));
        }
        redissonClient.shutdown();
    }

    private static String generateUrl(int start) {
        return baseUrl + "&tag=" + tag + "&sort=" + sort + "&start" + start;
    }
}
