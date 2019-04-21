package us.codecraft.webmagic.downloader.phantomJs;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.selector.PlainText;

import java.io.UnsupportedEncodingException;

public class PhantomDownloader extends AbstractDownloader {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private WebDriverPool webDriverPool = new WebDriverPool();

    @Override
    public Page download(Request request, Task task) {
        WebDriver driver = null;
        Page page = Page.fail();
        page.setRequest(request);
        try {
            driver = webDriverPool.get();
            if (task == null || task.getSite() == null) {
                throw new NullPointerException("task or site can not be null");
            }
            driver.get(request.getUrl());
            page.setRawText(driver.getPageSource());
            page.setStatusCode(200);
            page.setUrl(new PlainText(request.getUrl()));
            page.setBytes(page.getRawText().getBytes(request.getCharset()));
            page.setDownloadSuccess(true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("Charset error {}", request.getUrl(), e);
            page.setDownloadSuccess(false);
        } catch (InterruptedException e) {
            logger.error("PhantomJs downloader error {}", request.getUrl(), e);
            page.setDownloadSuccess(false);
        } finally {
            if (driver != null) {
                webDriverPool.returnToPool(driver);
            }
        }
        return page;
    }

    @Override
    public void setThread(int threadNum) {

    }
}
