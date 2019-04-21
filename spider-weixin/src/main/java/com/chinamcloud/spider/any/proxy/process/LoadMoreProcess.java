package com.chinamcloud.spider.any.proxy.process;

import com.alibaba.fastjson.JSON;
import com.chinamcloud.spider.any.proxy.pojo.RequestUrl;
import com.chinamcloud.spider.any.proxy.pojo.result.AppMsgExtInfo;
import com.chinamcloud.spider.any.proxy.pojo.result.CommMsgInfo;
import com.chinamcloud.spider.any.proxy.pojo.result.LoadMoreResult;
import com.chinamcloud.spider.any.proxy.pojo.result.MsgListInfo;
import com.chinamcloud.spider.any.proxy.util.RequestGenerate;
import com.chinamcloud.spider.weixin.process.IWeiXinProcess;
import org.apache.commons.lang3.SerializationUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import static com.chinamcloud.spider.any.proxy._const.AnyProxyConst.*;

public class LoadMoreProcess implements IWeiXinProcess{

    @Override
    public void process(Page page) {
        String json = page.getRawText();
        LoadMoreResult result = JSON.parseObject(json, LoadMoreResult.class);
        if (result.getRet() != 0) return;
        // 获取下一页数据
        if (result.getCanMsgContinue() != 0) {
            Request request = SerializationUtils.clone(page.getRequest());
            RequestUrl url = new RequestUrl(request.getUrl());
            url.setParam(OFFSET, String.valueOf(result.getNextOffset()));
            request.setUrl(url.toUrl());
            request.setExtractLinks(false);
            page.addTargetRequest(request);
        }

        // 获取文章URL
        for (MsgListInfo info:
             result.getGeneralMsgList().getList()) {
            int date = info.getCommMsgInfo().getDatetime();
            addContentRequest(info.getAppMsgExtInfo(), page, date);
            for (AppMsgExtInfo msgInfo:
                    info.getAppMsgExtInfo().getMultiAppMsgItemList()) {
                addContentRequest(msgInfo, page, date);
            }
        }
    }



    private void addContentRequest(AppMsgExtInfo msgExtInfo, Page page, int date) {
        Request request = RequestGenerate.content(msgExtInfo.getContentUrl(), page.getRequest().getExtra(BIZ).toString(), date);
        request.putExtra(COVER, msgExtInfo.getCover());
        request.putExtra(DIGEST, msgExtInfo.getDigest());
        page.addTargetRequest(request);
    }
}
