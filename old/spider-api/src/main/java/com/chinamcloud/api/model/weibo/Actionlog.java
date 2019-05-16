package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
       /**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Actionlog {

    private String oid;
    @JsonProperty("act_code")
    private int actCode;
    @JsonProperty("act_type")
    private int actType;
    private String source;
    public void setOid(String oid) {
         this.oid = oid;
     }
     public String getOid() {
         return oid;
     }

    public void setActCode(int actCode) {
         this.actCode = actCode;
     }
     public int getActCode() {
         return actCode;
     }

    public void setActType(int actType) {
         this.actType = actType;
     }
     public int getActType() {
         return actType;
     }

    public void setSource(String source) {
         this.source = source;
     }
     public String getSource() {
         return source;
     }

}