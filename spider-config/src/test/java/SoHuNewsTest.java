import com.chinamcloud.spider.ConfigTwoSpider;
import com.chinamcloud.spider.model.PageModel;
import com.chinamcloud.spider.orm.redis.RedisPool;
import com.chinamcloud.spider.pipeline.MongoPipeline;
import com.chinamcloud.spider.scheduler.ConfigTwoRedisScheduler;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;

import java.util.Arrays;

public class SoHuNewsTest {
    public static void main(String[] args) {
        Request request = new Request("https://www.thepaper.cn/newsDetail_forward_2712130");
        request.setInterval(1);
        PageModel pageModel = new PageModel();
        pageModel.setOwnProcess("PengPaiProcess");
        request.putExtra("PageModel", Arrays.asList(pageModel));
        ConfigTwoSpider configTwoSpider = ConfigTwoSpider.create(Site.me().setDomain("PoolOne"),
                new MongoPipeline("spider_news"));
        configTwoSpider.setScheduler(new ConfigTwoRedisScheduler(RedisPool.jedisPool()));
        configTwoSpider.addRequest(request);
        configTwoSpider.start();
    }
}
