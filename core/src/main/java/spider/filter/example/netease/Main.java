package spider.filter.example.netease;

import spider.Request;
import spider.Site;
import spider.Spider;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Site site = new Site();
        site.setDomain("music.163.com");
        Request request = new Request("http://music.163.com/discover/playlist", new CatFilter());
        List<Request> requests = new ArrayList<Request>();
        requests.add(request);
        Spider spider = new Spider(1, site, requests);
        spider.run();
    }
}
