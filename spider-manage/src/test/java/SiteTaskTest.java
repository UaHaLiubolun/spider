
import com.chinamcloud.spider.process.process.PengPaiProcess;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

public class SiteTaskTest {

    public static void main(String[] args) {

        Downloader downloader = new HttpClientDownloader();
        Page page = downloader.download(new Request("https://www.thepaper.cn/newsDetail_forward_2790855"), Site.me().toTask());
        PengPaiProcess pengPaiProcess = new PengPaiProcess();
        Object o = pengPaiProcess.process(page);
    }
}

