package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Info {

    @JsonProperty("url_short")
    private String urlShort;
    @JsonProperty("url_long")
    private String urlLong;
    private int type;
    private boolean result;
    private String title;
    private String description;
    @JsonProperty("last_modified")
    private int lastModified;
    private int transcode;
    @JsonProperty("ext_status")
    private int extStatus;
    public void setUrlShort(String urlShort) {
         this.urlShort = urlShort;
     }
     public String getUrlShort() {
         return urlShort;
     }

    public void setUrlLong(String urlLong) {
         this.urlLong = urlLong;
     }
     public String getUrlLong() {
         return urlLong;
     }

    public void setType(int type) {
         this.type = type;
     }
     public int getType() {
         return type;
     }

    public void setResult(boolean result) {
         this.result = result;
     }
     public boolean getResult() {
         return result;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setDescription(String description) {
         this.description = description;
     }
     public String getDescription() {
         return description;
     }

    public void setLastModified(int lastModified) {
         this.lastModified = lastModified;
     }
     public int getLastModified() {
         return lastModified;
     }

    public void setTranscode(int transcode) {
         this.transcode = transcode;
     }
     public int getTranscode() {
         return transcode;
     }

    public void setExtStatus(int extStatus) {
         this.extStatus = extStatus;
     }
     public int getExtStatus() {
         return extStatus;
     }

}