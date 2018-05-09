package spider.filter.example.netease;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import spider.Page;
import spider.Request;
import spider.filter.PageFilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayListFilter implements PageFilter {

    String url = "http://music.163.com";

    public void filter(Page page) {
        Document doc = Jsoup.parse(page.getRawText());
        page.setNewRequests(getPlayListsRequest(doc));
        page.getNewRequests().addAll(getPageRequest(doc));
    }


    private List<Request> getPageRequest(Document document) {
        Elements elements = document.select("a[class='zpgi']");
        return transToRequest(elements);
    }

    private List<Request> getPlayListsRequest(Document document) {
        Elements elements = document.select("a[class='msk']");
        return transToRequest(elements);
    }

    private List<Request> transToRequest(Elements elements) {
        Iterator<Element> iterator = elements.iterator();
        List<Request> newRequests = new ArrayList<Request>();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            Request request = new Request(url + element.attr("href"), new PlayListInfoFilter());
            newRequests.add(request);
        }
        return newRequests;
    }
}
