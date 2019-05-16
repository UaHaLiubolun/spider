package com.chinamcloud.spider.orm.mongo;


import com.chinamcloud.spider.Const;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class MongoJDBC {

    private static String host;
    private static int port;
    private static String userName;
    private static String pw;

    static {
        if (Const.environment.equals("dev")) {
            host = "172.29.4.69";
            port = 27017;
            userName = "root";
            pw = "";
        } else if (Const.environment.equals("prod")) {
            host = "dds-2zea7e0ad1a732741.mongodb.rds.aliyuncs.com";
            userName = "root";
            pw = "Newmed!@s0bey";
            port = 3717;
        } else {
            host = "localhost";
            port = 27017;
            userName = "root";
            pw = "Newmed!@s0bey";
        }
    }


    public static MongoClient mongoClient() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = new MongoClient();
        if (!pw.equals("")) {
                MongoCredential credential = MongoCredential.createScramSha1Credential(userName,
                    "admin",
                    pw.toCharArray());
                mongoClient = new MongoClient(new ServerAddress(host, port), Arrays.asList(credential), MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        } else {
            mongoClient = new MongoClient(new ServerAddress(host, port), MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        }

        return mongoClient;
    }


    public static MongoDatabase getDataBase(String dataBase) {
        try {
            return mongoClient().getDatabase(dataBase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
