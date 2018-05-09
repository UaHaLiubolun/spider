package spider;

import java.util.List;

public class Page {

    private Request request;

    private String rawText;

    private List<Request> newRequests;

    public List<Request> getNewRequests() {
        return newRequests;
    }

    public void setNewRequests(List<Request> newRequests) {
        this.newRequests = newRequests;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }
}
