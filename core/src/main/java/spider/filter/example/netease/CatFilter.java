package spider.filter.example.netease;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import spider.Page;
import spider.Request;
import spider.filter.PageFilter;

import java.util.ArrayList;
import java.util.List;

public class CatFilter implements PageFilter {

    String url = "http://music.163.com/discover/playlist/?cat=";

    public void filter(Page page) {
        Document doc = Jsoup.parse(page.getRawText());
        Elements elements = doc.select("a[class='s-fc1']");
        List<Request> newRequests = new ArrayList<Request>();
        for (String text : elements.eachText()) {
            Request request = new Request(url + text, new PlayListFilter());
            newRequests.add(request);
        }
        page.setNewRequests(newRequests);
    }
}
