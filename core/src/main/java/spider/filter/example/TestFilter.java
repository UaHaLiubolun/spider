package spider.filter.example;

import spider.Page;
import spider.Request;
import spider.filter.PageFilter;

import java.util.ArrayList;
import java.util.List;

public class TestFilter implements PageFilter {

    public void filter(Page page) {
        Request request = new Request();
        request.setUrl("http://baike.baidu.com/search/word?word=水力发电&pic=1&sug=1&enc=utf8");
        request.setFilter(new TestFilter());
        List<Request> requests = new ArrayList<Request>();
//        requests.add(request);
//        requests.add(request);
        page.setNewRequests(requests);
    }
}
