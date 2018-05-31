package spider;

public class Task {

    private String uuid;

    private Site site;

    private Request request;

    private boolean success = false;

    private int retryTime = 0;

    public Task(Site site, Request request) {
        this.site = site;
        this.request = request;
    }

    public boolean isSuccess() {
        return success;
    }

    public void success() {
        this.success = true;
    }

    public void setRetryTime(int retryTime) {
        this.retryTime = retryTime;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public void error() {
        retryTime ++;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
