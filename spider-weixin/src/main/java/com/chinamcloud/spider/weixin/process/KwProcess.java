package com.chinamcloud.spider.weixin.process;

import com.chinamcloud.spider.weixin.util.WeiXinRequestGenerate;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Html;

import java.util.Iterator;

public class KwProcess implements IWeiXinProcess {

    @Override
    public void process(Page page) {
        Elements elements = page.getHtml().getDocument().getElementsByClass("news-list");
        Iterator<Element> iterator = elements.get(0).children().iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            Html html = new Html(element.outerHtml());
            String cover = html.xpath("//div[@class='img-box']//img/@src").get();
            String url = html.xpath("//h3//a/@href").get();
            String gzhName = html.xpath("//div[@class='s-p']/a/text()").get();
            String digest = html.xpath("//p[@class='txt-info']/newsText()").get();
            String data = html.xpath("//div[@class='s-p']/@t").get();
            String gzhId = "公众号关键字_" + page.getRequest().getExtra("kw").toString();
            Request request = WeiXinRequestGenerate.contentRequest(url, gzhId, null, gzhName);
            request.setExtractLinks(true);
            request.putExtra("date", data);
            request.putExtra("digest", digest);
            request.putExtra("imgUrl", cover);
            page.addTargetRequest(request);
        }

    }
}
