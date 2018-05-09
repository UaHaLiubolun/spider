package spider;

import spider.filter.example.TestFilter;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Site site = new Site();
        site.setDomain("www.baidu.com");
        Request request = new Request();
        request.setFilter(new TestFilter());
        request.setUrl("http://baike.baidu.com/search/word?word=水力发电&pic=1&sug=1&enc=utf8");
        List<Request> requests = new ArrayList<Request>();
        requests.add(request);
        Spider spider = new Spider(1, site, requests);
        spider.run();
    }

}
