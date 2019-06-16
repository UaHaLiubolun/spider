package cn.stark.spider.download;

import cn.stark.spider.common.Request;
import cn.stark.spider.common.spider.Scheduler;
import cn.stark.spider.common.spider.scheduler.RedisScheduler;
import cn.stark.spider.common.utils.RedisConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

public class AddUrl {

    private static String baseUrl = "http://movie.douban.com/j/new_search_subjects?";

    private static String tag = "电影";

    private static String range = "7,10";

    /**
     * 排序
     * S 评分
     * T 标记
     * U 热门
     * R 最新
     */
    private static String sort = "U";

    /**
     * 类型
     */
    private static String genres = "";

    /**
     * 国家
     */
    private static String countries = "";

    /**
     * 年代
     */
    private static String year_range = "";

    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create(RedisConfig.getConfig());
        Scheduler<Request> requestScheduler = new RedisScheduler<>("request", redissonClient);
        for (int i = 0; i < 1; i++) {
            requestScheduler.push(new Request(generateUrl(i * 20)));
        }
        redissonClient.shutdown();
    }

    private static String generateUrl(int start) {
        String url =  baseUrl + "range=" + range + "&tags=" + tag + "&sort=" + sort + "&start=" + start;
        if (!countries.equals("")) {
            url += ("&countries=" + countries);
        }
        if (!genres.equals("")) {
            url += ("&genres=" + genres);
        }
        if (!year_range.equals("")) {
            url += ("&year_range=" + year_range);
        }
        return url;
    }
}
