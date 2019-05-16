package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class WeiBoModel {

    @JsonProperty("card_type")
    private int cardType;
    @JsonProperty("card_type_name")
    private String cardTypeName;
    private String itemid;
    private Actionlog actionlog;
    @JsonProperty("display_arrow")
    private int displayArrow;
    @JsonProperty("show_type")
    private int showType;
    private Mblog mblog;
    private String scheme;
    public void setCardType(int cardType) {
         this.cardType = cardType;
     }
     public int getCardType() {
         return cardType;
     }

    public void setCardTypeName(String cardTypeName) {
         this.cardTypeName = cardTypeName;
     }
     public String getCardTypeName() {
         return cardTypeName;
     }

    public void setItemid(String itemid) {
         this.itemid = itemid;
     }
     public String getItemid() {
         return itemid;
     }

    public void setActionlog(Actionlog actionlog) {
         this.actionlog = actionlog;
     }
     public Actionlog getActionlog() {
         return actionlog;
     }

    public void setDisplayArrow(int displayArrow) {
         this.displayArrow = displayArrow;
     }
     public int getDisplayArrow() {
         return displayArrow;
     }

    public void setShowType(int showType) {
         this.showType = showType;
     }
     public int getShowType() {
         return showType;
     }

    public void setMblog(Mblog mblog) {
         this.mblog = mblog;
     }
     public Mblog getMblog() {
         return mblog;
     }

    public void setScheme(String scheme) {
         this.scheme = scheme;
     }
     public String getScheme() {
         return scheme;
     }

}