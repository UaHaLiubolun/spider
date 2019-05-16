package com.chinamcloud.spider.orm.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class Producer {

    private static org.apache.kafka.clients.producer.Producer<String, String> producer;

    static {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.11.238:9092,192.168.10.26:9092,192.168.12.161:9092");//服务器ip:端口号，集群用逗号分隔
//        props.put("bootstrap.servers", " 39.105.252.3:9092");//服务器ip:端口号，集群用逗号分隔
        props.put("acks", "all");
        props.put("retries", 3);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

    public static org.apache.kafka.clients.producer.Producer<String, String> get() {
        return producer;
    }
}
