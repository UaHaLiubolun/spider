package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class PageInfo {

    @JsonProperty("page_pic")
    private PagePic pagePic;
    @JsonProperty("page_url")
    private String pageUrl;
    @JsonProperty("page_title")
    private String pageTitle;
    private String content1;
    private String content2;
    private String type;
    @JsonProperty("media_info")
    private MediaInfo mediaInfo;
    @JsonProperty("play_count")
    private int playCount;
    @JsonProperty("object_id")
    private String objectId;
    public void setPagePic(PagePic pagePic) {
         this.pagePic = pagePic;
     }
     public PagePic getPagePic() {
         return pagePic;
     }

    public void setPageUrl(String pageUrl) {
         this.pageUrl = pageUrl;
     }
     public String getPageUrl() {
         return pageUrl;
     }

    public void setPageTitle(String pageTitle) {
         this.pageTitle = pageTitle;
     }
     public String getPageTitle() {
         return pageTitle;
     }

    public void setContent1(String content1) {
         this.content1 = content1;
     }
     public String getContent1() {
         return content1;
     }

    public void setContent2(String content2) {
         this.content2 = content2;
     }
     public String getContent2() {
         return content2;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setMediaInfo(MediaInfo mediaInfo) {
         this.mediaInfo = mediaInfo;
     }
     public MediaInfo getMediaInfo() {
         return mediaInfo;
     }

    public void setPlayCount(int playCount) {
         this.playCount = playCount;
     }
     public int getPlayCount() {
         return playCount;
     }

    public void setObjectId(String objectId) {
         this.objectId = objectId;
     }
     public String getObjectId() {
         return objectId;
     }

}