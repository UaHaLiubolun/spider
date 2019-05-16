package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Visible {

    private int type;
    @JsonProperty("list_id")
    private int listId;
    public void setType(int type) {
         this.type = type;
     }
     public int getType() {
         return type;
     }

    public void setListId(int listId) {
         this.listId = listId;
     }
     public int getListId() {
         return listId;
     }

}