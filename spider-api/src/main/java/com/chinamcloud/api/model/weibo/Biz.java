package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Biz {

    @JsonProperty("biz_id")
    private String bizId;
    private String containerid;
    public void setBizId(String bizId) {
         this.bizId = bizId;
     }
     public String getBizId() {
         return bizId;
     }

    public void setContainerid(String containerid) {
         this.containerid = containerid;
     }
     public String getContainerid() {
         return containerid;
     }

}