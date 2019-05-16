import com.chinamcloud.spider.orm.redis.RedisPool;
import com.chinamcloud.spider.weixin.download.WeChatDownloader;
import com.chinamcloud.spider.weixin.download.v2.WeChatHttpDownloader;
import com.chinamcloud.spider.weixin.pipeline.HttpPipeline;
import com.chinamcloud.spider.weixin.pipeline.WeiXinPipeline;
import com.chinamcloud.spider.weixin.process.WeiXinProcess;
import com.chinamcloud.spider.weixin.scheduler.WeChatSecheduler;
import com.chinamcloud.spider.weixin.util.WeiXinRequestGenerate;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;

public class SingleWeiXinSpider {

    public static void main(String[] args) {
        Downloader downloader = new WeChatHttpDownloader();
        Spider spider = Spider.create(new WeiXinProcess(Site.me())).thread(1).addPipeline(new WeiXinPipeline()).setDownloader(downloader);
        spider.addPipeline(new HttpPipeline());
        spider.addRequest(WeiXinRequestGenerate.searchRequest("sytv1994 ", "WeiXin", "顺义传媒"));
        spider.setScheduler(new WeChatSecheduler(RedisPool.jedisPool()));
        spider.setExitWhenComplete(false);
        spider.run();
    }
}
