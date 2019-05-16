package com.chinamcloud.spider.weixin.util.feifei;

public class Hello {
    public static void main( String[] args) throws Exception {
        Api api = new Api();
        String app_id = "100001";
        String app_key = "123456";
        String pd_id = "100000";
        String pd_key = "123456";
        // 对象生成之后，在任何操作之前，需要先调用初始化接口
        api.Init(app_id, app_key, pd_id, pd_key);
        // 查询余额
        Util.HttpResp resp = api.QueryBalc();
        System.out.printf("query balc!ret: %d cust: %f err: %s reqid: %s pred: %s\n", resp.ret_code, resp.cust_val, resp.err_msg, resp.req_id, resp.pred_resl);
        // 具体类型可以查看官方网站的价格页选择具体的类型，不清楚类型的，可以咨询客服
        String pred_type = "30400";
        // 通过文件进行验证码识别,请使用自己的图片文件替换
        String img_file = "./img.jpg";
        resp    = api.PredictFromFile(pred_type, img_file);
        // 如果是通过url或者截图得到二进制图片数据的，可以直接调用下面的接口
        // resp = api.Predict(pred_type, img_data);
        System.out.printf("predict from file!ret: %d cust: %f err: %s reqid: %s pred: %s\n", resp.ret_code, resp.cust_val, resp.err_msg, resp.req_id, resp.pred_resl);
        boolean jflag      = false;
        if( jflag) {
            // 识别的结果如果与预期不符，可以调用这个接口将预期不符的订单退款
            // 退款仅在正常识别出结果后，无法通过网站验证的情况，请勿非法或者滥用，否则可能进行封号处理
            resp = api.Justice(resp.req_id);
            System.out.printf("justice !ret: %d cust: %f err: %s reqid: %s pred: %s\n", resp.ret_code, resp.cust_val, resp.err_msg, resp.req_id, resp.pred_resl);
        }
        // 开发者可以选择将充值接口集成到软件中，普通用户可以使用充值卡直接进行充值
        // 普通用户也可以选择到官方网站直接进行充值
        //String card_id = "36317545";
        //String card_key = "123456";
        //resp    = api.Charge(card_id, card_key);
        //System.out.printf("charge !ret: %d cust: %f err: %s reqid: %s pred: %s\n", resp.ret_code, resp.cust_val, resp.err_msg, resp.req_id, resp.pred_resl);
    }
}
