package cn.stark.spider.download.proxy;

import lombok.Data;

import java.util.List;

@Data
public class ProxyResult {

    private String code;

    private List<Msg> msg;

    @Data
    class Msg {
        String port;
        String ip;
    }

    public Proxy get() {
        if (msg == null || msg.size() == 0){
            return null;
        }
        return new Proxy(msg.get(0).getIp(), Integer.parseInt(msg.get(0).getPort()));
    }

}

