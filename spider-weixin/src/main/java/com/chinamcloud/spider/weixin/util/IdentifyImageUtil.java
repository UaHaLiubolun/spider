package com.chinamcloud.spider.weixin.util;

import com.chinamcloud.spider.weixin.util.feifei.Api;
import com.chinamcloud.spider.weixin.util.feifei.Util;

public class IdentifyImageUtil {

    private static Api api;

    static {
        api = new Api();
        api.Init("305040",
                "N9kbPKr4Psrgb/E6zUa9m7/DsOG/f2Ri",
                "105040",
                "WIZlDHSmmzVvBM7jAUqsva45k/u0exFs");
    }

    public static String verifyCode(byte[] img_file, String typeCode) {
        try {
            if (img_file == null) return null;
            Util.HttpResp resp = api.QueryBalc();
            String pred_type = typeCode;
            resp    = api.Predict(pred_type, img_file);
            if (resp.ret_code == 0) {
                return resp.pred_resl;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
