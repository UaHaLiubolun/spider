package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class PostLinks {

    private String link;
    @JsonProperty("android_native_link")
    private String androidNativeLink;
    @JsonProperty("native_link")
    private String nativeLink;
    public void setLink(String link) {
         this.link = link;
     }
     public String getLink() {
         return link;
     }

    public void setAndroidNativeLink(String androidNativeLink) {
         this.androidNativeLink = androidNativeLink;
     }
     public String getAndroidNativeLink() {
         return androidNativeLink;
     }

    public void setNativeLink(String nativeLink) {
         this.nativeLink = nativeLink;
     }
     public String getNativeLink() {
         return nativeLink;
     }

}