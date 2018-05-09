package spider;

import spider.pipeline.Pipeline;
import java.util.List;
import java.util.Map;

public class Page {

    private Request request;

    private String rawText;

    private List<Request> newRequests;

    private List<Pipeline> pipelines;

    private Map<String, Object> items;

    public <T> T getItem(String key) {
        Object o = items.get(key);
        if (o == null) return null;
        return (T) items.get(key);
    }

    public <T> void putItem(String key, T value) {
        items.put(key, value);
    }

    public Map<String, Object> getItems() {
        return items;
    }

    public void setItems(Map<String, Object> items) {
        this.items = items;
    }

    public List<Pipeline> getPipelines() {
        return pipelines;
    }

    public void setPipelines(List<Pipeline> pipelines) {
        this.pipelines = pipelines;
    }

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
