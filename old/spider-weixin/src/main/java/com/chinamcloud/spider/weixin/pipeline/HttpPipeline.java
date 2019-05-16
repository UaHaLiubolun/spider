package com.chinamcloud.spider.weixin.pipeline;


import com.alibaba.fastjson.JSON;
import com.chinamcloud.spider.weixin.util.HttpUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


public class HttpPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        if (resultItems.get("content") != null) {
            try {
                String content = "[" + JSON.toJSONString(resultItems.get("content")) + "]";
                String result = HttpUtil.executePost("http://spider-api.chinamcloud.com/kafka?auth=kafka", content);
                System.out.println(result);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
