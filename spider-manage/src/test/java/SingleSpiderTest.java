import com.chinamcloud.spider.ConfigTwoSpider;
import com.chinamcloud.spider.dao.NewsListDao;
import com.chinamcloud.spider.dao.SiteTaskDao;
import com.chinamcloud.spider.model.SiteTask;
import com.chinamcloud.spider.orm.redis.RedisPool;
import com.chinamcloud.spider.orm.redis.RedisUtil;
import com.chinamcloud.spider.pipeline.MongoPipeline;
import com.chinamcloud.spider.scheduler.ConfigTwoRedisScheduler;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.phantomJs.PhantomDownloader;
import us.codecraft.webmagic.model.HttpRequestBody;

import java.util.HashMap;
import java.util.Map;

public class SingleSpiderTest {

    private static NewsListDao newsListDao;

    private static SiteTaskDao siteTaskDao;

    static {
        newsListDao = new NewsListDao();
        siteTaskDao = new SiteTaskDao();
    }

    private static void tranRequestBody(Request request, SiteTask siteTask) {
        if (siteTask.getContentType().equals(HttpRequestBody.ContentType.JSON)) {
            request.setRequestBody(HttpRequestBody.json(siteTask.getBody(), siteTask.getCharset()));
        } else if (siteTask.getContentType().equals(HttpRequestBody.ContentType.FORM)) {
            request.setRequestBody(HttpRequestBody.form(tranFormData(siteTask.getBody()), siteTask.getCharset()));
        }
    }

    private static Map<String, Object> tranFormData(String content) {
        String[] ss = content.split("&");
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < ss.length; i++) {
            String[] m = ss[i].split("=");
            map.put(m[0], m[1]);
        }
        return map;
    }

    private static void pullRedis(SiteTask siteTask) {
        Request request = new Request(siteTask.getUrl());
        request.setSourceUrl("Header");
        request.setExtractLinks(true);
        request.setTest(true);
        request.setMethod(siteTask.getMethod());
        if (!siteTask.getMethod().equalsIgnoreCase("get")) {
            tranRequestBody(request, siteTask);
        }
        request.setInterval(siteTask.getInterval());
        request.putExtra("PageModel", siteTask.getPageModels());
        request.putExtra("source", siteTask.getSource());
        request.putExtra("type", siteTask.getType());
        request.setCharset(siteTask.getCharset());
        request.setTest(siteTask.isTest());
        request.putExtra("sourceName", newsListDao.getNameByUrl(siteTask.getUrl()));
        RedisUtil.pushRequest("queue_spider_list", request);
    }

    public static void main(String[] args) {
        SiteTask siteTask = siteTaskDao.getByUrl("https://www.thepaper.cn/");
        pullRedis(siteTask);
        ConfigTwoSpider configTwoSpider = ConfigTwoSpider.create(Site.me().setDomain("PoolOne"),
                new MongoPipeline("spider_news"));
        configTwoSpider.setDownloader(new PhantomDownloader());
        configTwoSpider.setScheduler(new ConfigTwoRedisScheduler(RedisPool.jedisPool()))
                .thread(1);
        configTwoSpider.run();
    }

}
