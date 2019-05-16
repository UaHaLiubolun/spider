package com.chinamcloud.api.controller;

import com.alibaba.fastjson.JSON;
import com.chinamcloud.api.CodeResult;
import com.chinamcloud.api.dao.GzhContentDao;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UtilController {

    private Producer producer = com.chinamcloud.spider.orm.kafka.Producer.get();
    private GzhContentDao gzhContentDao = new GzhContentDao();


    @PostMapping("/kafka")
    public CodeResult pushKafka(@RequestParam(value = "auth") String auth,
                                @RequestBody List<Map> list) {
        if (!auth.equals("kafka")) {
            return CodeResult.failedResult("auth错误");
        }
        list.forEach(l -> {
            try {
                producer.send(new ProducerRecord<>("cralwer", String.valueOf(l.hashCode()),
                        JSON.toJSONString(l)));
                gzhContentDao.insert(l);
                System.out.println("send msg to kafka success");
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        return CodeResult.successResult();
    }
}
