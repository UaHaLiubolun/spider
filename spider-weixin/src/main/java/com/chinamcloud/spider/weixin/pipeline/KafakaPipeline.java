package com.chinamcloud.spider.weixin.pipeline;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.Producer;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


public class KafakaPipeline implements Pipeline {


    private Producer producer = com.chinamcloud.spider.orm.kafka.Producer.get();

    @Override
    public void process(ResultItems resultItems, Task task) {
        if (resultItems.get("content") != null) {
            try {
                producer.send(new ProducerRecord<>("cralwer", String.valueOf(resultItems.hashCode()),
                        JSON.toJSONString(resultItems.get("content"))));
                System.out.println("send msg to kafka success");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
