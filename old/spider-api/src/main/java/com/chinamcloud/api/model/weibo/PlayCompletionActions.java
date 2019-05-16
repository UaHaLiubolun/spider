package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class PlayCompletionActions {

    private String type;
    private String icon;
    private String text;
    private String link;
    @JsonProperty("btn_code")
    private int btnCode;
    @JsonProperty("show_position")
    private int showPosition;
    private Actionlog actionlog;
    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setIcon(String icon) {
         this.icon = icon;
     }
     public String getIcon() {
         return icon;
     }

    public void setText(String text) {
         this.text = text;
     }
     public String getText() {
         return text;
     }

    public void setLink(String link) {
         this.link = link;
     }
     public String getLink() {
         return link;
     }

    public void setBtnCode(int btnCode) {
         this.btnCode = btnCode;
     }
     public int getBtnCode() {
         return btnCode;
     }

    public void setShowPosition(int showPosition) {
         this.showPosition = showPosition;
     }
     public int getShowPosition() {
         return showPosition;
     }

    public void setActionlog(Actionlog actionlog) {
         this.actionlog = actionlog;
     }
     public Actionlog getActionlog() {
         return actionlog;
     }

}