package com.chinamcloud.spider.weixin.process;

import com.chinamcloud.spider.weixin.util.WeiXinRequestGenerate;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchProcess implements IWeiXinProcess {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static  Invocable invoca =null;
    private final  String  LINK_ROOT = "https://weixin.sogou.com";

    static {
        try {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("javascript");
            engine.eval(readJsFile());
            if(invoca ==null){
                invoca = (Invocable) engine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //��ȡjs���룬ת��URL
    private static  String readJsFile() throws IOException {
        StringBuffer script = new StringBuffer();
        File file = new File(SearchProcess.class.getResource("/url_convert.js").getPath());
        FileReader filereader = new FileReader(file);
        BufferedReader bufferreader = new BufferedReader(filereader);
        String tempString = null;
        while ((tempString = bufferreader.readLine()) != null) {
            script.append(tempString).append("\n");
        }
        bufferreader.close();
        filereader.close();
        return script.toString();
    }


    @Override
    public void process(Page page) {
        if (!checkType(page)) return;
        String url = page.getHtml().xpath("//ul[@class='news-list2']//li[1]//p[@class='tit']/a/@href").get();
        //��ȡ��ת����
        String realUrl =null;
        try {
            Object target_url = (Object) invoca.invokeFunction("convert",url);
            if(target_url ==null){
                return;
            }
            String tar_link = target_url.toString();
            if(!tar_link.contains(LINK_ROOT)){
                tar_link = LINK_ROOT + tar_link;
            }
            realUrl = parseRealUrl(page, tar_link);
            if(StringUtils.isBlank(realUrl)){
                return;
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        String gzhName = page.getRequest().getExtra("gzhName").toString();
        Request request = WeiXinRequestGenerate.gzhUrl(realUrl, page.getRequest().getExtra("gzhId").toString(),
                page.getRequest().getExtra("classification").toString(), gzhName);
        page.addTargetRequest(request);
    }

    private boolean checkType(Page page) {
        if (page.getRequest().getExtra("ProcessType") == null ||
            !page.getRequest().getExtra("ProcessType").equals("search")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * parse hidden url  from  js
     * <meta content="always" name="referrer">
     * <script>
     *     var url = '';
     *     url += 'http://mp.w';
     *     url += 'eixin.qq.co';
     *     url += 'm/profile?s';
     *     url += 'rc=3&timest';
     *     url += 'amp=1555573';
     *     url += '349&ver=1&s';
     *     url += 'ignature=aJ';
     *     url += 'tpXKXpdQhiT';
     *     url += 'gU0GujI-r9K';
     *     url += 'lqiBIQFRbvE';
     *     url += 'k3PSeoOH93-xhqkHO6HoslCfJmBqfPrUVtDm7oFmyYM*tz1zG1g==';
     *     url.replace("@", "");
     *     window.location.replace(url)
     * </script>
     *
     */


    private String parseRealUrl(Page page,String target_url){
        CloseableHttpResponse response =null;
        try {
            CloseableHttpClient httpCilent = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(target_url);
            httpGet.setHeader("Cookie",page.getCookie());
            httpGet.setHeader("Referer",page.getRequest().getUrl());
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134");
            response   =  httpCilent.execute(httpGet);
            String html = EntityUtils.toString(response.getEntity(),"UTF-8");
            if(html.contains("��֤��")){
                return null ;
            }else{
                //��ȡjs�����������µ�ַ
                //ex http://mp.weixin.qq.com/profile?src=3&timestamp=1555575761&ver=1&signature=g8kqiJruPqJpPuZXlKDJE57D5nMKdojPdUIMv95PI8b4gCE*OWHHfvWJvpLo8WGpdI1BX0d-gPsPc3vmOzPufA==
                final  Pattern pattern = Pattern.compile("(?<=\')([\\S]+?)(?=\')");
                Matcher matcherTime = pattern.matcher(html);
                StringBuffer url = new StringBuffer();
                while (matcherTime.find()) {
                    url.append(matcherTime.group());
                }
                System.out.println(url.toString());
                return url.toString();
            }
        } catch (IOException e) {
            logger.error("downloading target URL error {}",target_url);
            return null;
        }
    }
}
