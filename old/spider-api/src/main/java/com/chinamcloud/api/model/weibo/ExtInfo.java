package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class ExtInfo {

    @JsonProperty("video_orientation")
    private String videoOrientation;
    public void setVideoOrientation(String videoOrientation) {
         this.videoOrientation = videoOrientation;
     }
     public String getVideoOrientation() {
         return videoOrientation;
     }

}