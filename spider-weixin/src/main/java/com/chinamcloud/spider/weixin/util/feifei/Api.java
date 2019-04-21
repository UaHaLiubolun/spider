package com.chinamcloud.spider.weixin.util.feifei;
import java.util.Date;
import java.net.URLEncoder;

public class Api {
    protected String app_id;
    protected String app_key;
    protected String usr_id;
    protected String usr_key;
    protected String pred_url;
    public void Init(String app_id, String app_key, String usr_id, String usr_key){
        this.app_id     = app_id;
        this.app_key    = app_key;
        this.usr_id     = usr_id;
        this.usr_key    = usr_key;
        this.pred_url   = "http://pred.fateadm.com";
    }

    public Util.HttpResp QueryBalc() throws Exception{
        long cur_tm     = new Date().getTime()/1000;
        String stm      = String.valueOf(cur_tm);
        String sign     = Util.CalcSign( usr_id, usr_key, stm);
        String url      = this.pred_url + "/api/custval";
        String params   = "user_id="+this.usr_id + "&timestamp=" + stm + "&sign=" + sign;
        String pres     = Util.HttpPost(url, params);
        Util.HttpResp resp = Util.ParseHttpResp( pres);
        return resp;
    }
    public Util.HttpResp Charge(String cardid, String cardkey) throws Exception{
        long cur_tm     = new Date().getTime()/1000;
        String stm      = String.valueOf(cur_tm);
        String sign     = Util.CalcSign( usr_id, usr_key, stm);
        String csign    = Util.CalcMd5(usr_key + stm + cardid + cardkey);
        String url      = this.pred_url + "/api/charge";
        String params   = "user_id=" + usr_id + "&timestamp=" + stm + "&sign=" + sign + "&cardid=" + cardid + "&csign=" + csign;
        String pres     = Util.HttpPost(url, params);
        Util.HttpResp resp  = Util.ParseHttpResp(pres);
        return resp;
    }
    public Util.HttpResp PredictFromFile(String pred_type, String file_name)throws Exception{
        byte[] file_data    = Util.ReadBinaryFile(file_name);
        if( file_data == null){
            Util.HttpResp resp  = new Util.HttpResp();
            resp.ret_code       = -1;
            resp.err_msg        = "ERROR: read file failed! file_name: " + file_name;
            return resp;
        }
        Util.HttpResp resp  = Predict(pred_type, file_data);
        return resp;
    }
    public Util.HttpResp Predict(String pred_type, byte[] file_data) throws Exception{
        long cur_tm     = new Date().getTime()/1000;
        String stm      = String.valueOf(cur_tm);
        String sign     = Util.CalcSign( usr_id, usr_key, stm);
        String img_data = Util.CalcBase64(file_data);
        img_data        = URLEncoder.encode( img_data, "utf-8");
        String url      = pred_url + "/api/capreg";
        String params   = "user_id=" + usr_id + "&timestamp="+stm + "&sign=" + sign +"&predict_type=" + pred_type;
        if(!app_id.isEmpty()){
            String asign = Util.CalcSign( app_id, app_key, stm);
            params      = params + "&appid=" + app_id + "&asign=" + asign;
        }
        params          += "&img_data=" + img_data;
        //System.out.printf("file_data len: %d img_data: %d params: %s\n", file_data.length, img_data.length(), params);
        String pres     = Util.HttpPost(url, params);
        Util.HttpResp resp  = Util.ParseHttpResp(pres);
        return resp;
    }
    public Util.HttpResp Justice(String req_id) throws Exception{
        long cur_tm     = new Date().getTime()/1000;
        String stm      = String.valueOf(cur_tm);
        String sign     = Util.CalcSign( usr_id, usr_key, stm);
        String url      = pred_url + "/api/capjust";
        String params   = "user_id=" + usr_id + "&timestamp="+stm + "&sign=" + sign + "&request_id=" + req_id;
        String pres     = Util.HttpPost(url, params);
        Util.HttpResp resp  = Util.ParseHttpResp( pres);
        return resp;
    }
}
