package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class MObject {

    @JsonProperty("object_id")
    private String objectId;
    private String containerid;
    @JsonProperty("object_domain_id")
    private String objectDomainId;
    @JsonProperty("object_type")
    private String objectType;
    @JsonProperty("safe_status")
    private int safeStatus;
    @JsonProperty("show_status")
    private String showStatus;
    @JsonProperty("act_status")
    private String actStatus;
    @JsonProperty("last_modified")
    private String lastModified;
    private long timestamp;
    private long uuid;
    @JsonProperty("activate_status")
    private String activateStatus;
    private MObject object;
    public void setObjectId(String objectId) {
         this.objectId = objectId;
     }
     public String getObjectId() {
         return objectId;
     }

    public void setContainerid(String containerid) {
         this.containerid = containerid;
     }
     public String getContainerid() {
         return containerid;
     }

    public void setObjectDomainId(String objectDomainId) {
         this.objectDomainId = objectDomainId;
     }
     public String getObjectDomainId() {
         return objectDomainId;
     }

    public void setObjectType(String objectType) {
         this.objectType = objectType;
     }
     public String getObjectType() {
         return objectType;
     }

    public void setSafeStatus(int safeStatus) {
         this.safeStatus = safeStatus;
     }
     public int getSafeStatus() {
         return safeStatus;
     }

    public void setShowStatus(String showStatus) {
         this.showStatus = showStatus;
     }
     public String getShowStatus() {
         return showStatus;
     }

    public void setActStatus(String actStatus) {
         this.actStatus = actStatus;
     }
     public String getActStatus() {
         return actStatus;
     }

    public void setLastModified(String lastModified) {
         this.lastModified = lastModified;
     }
     public String getLastModified() {
         return lastModified;
     }

    public void setTimestamp(long timestamp) {
         this.timestamp = timestamp;
     }
     public long getTimestamp() {
         return timestamp;
     }

    public void setUuid(long uuid) {
         this.uuid = uuid;
     }
     public long getUuid() {
         return uuid;
     }

    public void setActivateStatus(String activateStatus) {
         this.activateStatus = activateStatus;
     }
     public String getActivateStatus() {
         return activateStatus;
     }

    public void setObject(MObject object) {
         this.object = object;
     }
     public MObject getObject() {
         return object;
     }

}