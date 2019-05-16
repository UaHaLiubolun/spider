package com.chinamcloud.spider.pipeline;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import us.codecraft.webmagic.Task;

import java.util.Date;
import java.util.Map;

public class KafakaPipeline implements ObjectPipeline{


    private static Producer<String, String> producer = com.chinamcloud.spider.orm.kafka.Producer.get();

    @Override
    public void process(Object o, Task task) {
        Map map = (Map) o;
        map.put("createdAt", new Date().getTime());
        //TODO 语言暂时写死
        map.put("langType", "ch");
        map.put("sourceId", Math.abs(map.get("tbNickName").hashCode()));
        map.put("isCralwer", 1);
        if (map.get("isTest") == null || !(boolean)map.get("isTest")) {
            producer.send(new ProducerRecord<>("cralwer", map.get("refererUrl").toString(),
                    JSON.toJSONString(map)));
        }
    }
}
