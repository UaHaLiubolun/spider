package com.chinamcloud.api.model.weibo;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Longtext {

    @JsonProperty("longTextContent")
    private String longtextcontent;
    @JsonProperty("url_objects")
    private List<UrlObjects> urlObjects;
    public void setLongtextcontent(String longtextcontent) {
         this.longtextcontent = longtextcontent;
     }
     public String getLongtextcontent() {
         return longtextcontent;
     }

    public void setUrlObjects(List<UrlObjects> urlObjects) {
         this.urlObjects = urlObjects;
     }
     public List<UrlObjects> getUrlObjects() {
         return urlObjects;
     }

}