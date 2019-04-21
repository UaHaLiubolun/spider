package com.chinamcloud.api.controller;


import com.chinamcloud.api.CodeResult;
import com.chinamcloud.api.dao.ListInfoDao;
import com.chinamcloud.api.dao.NewsListDao;
import com.chinamcloud.api.model.ListInfo;
import com.chinamcloud.spider.dao.SiteTaskDao;
import com.chinamcloud.spider.model.SiteTask;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Site;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.*;


@RestController
@CrossOrigin
public class SiteTaskController {

    @Resource(name = "newsListDao")
    private NewsListDao newsListDao;

    private SiteTaskDao siteTaskDao;

    {
        siteTaskDao = new SiteTaskDao();
    }


    /**
     * 获取所有站点
     * @return List<SiteTask>
     */
    @GetMapping("/siteTask")
    public CodeResult getAll() {
        return CodeResult.successResult(null, siteTaskDao.getAll());
    }

    @GetMapping("/siteTask/url")
    public CodeResult getByUrl(@RequestParam(value = "url") String url) {
        return CodeResult.successResult(null, siteTaskDao.getByUrl(url));
    }

    /**
     * 通过UrlMd5 获取站点
     * @param urlMd5 urlMd5
     * @return SiteTask
     */
    @GetMapping("/siteTask/urlMd5")
    public CodeResult getBuUrlMd5(@RequestParam(value = "urlMd5") String urlMd5) {
        Map<String, String> listInfo = newsListDao.getByUrl(urlMd5);
        if (listInfo != null) {
            SiteTask siteTask = siteTaskDao.getByUrl(listInfo.get("url"));
            if (siteTask == null) {
                siteTask = new SiteTask();
                siteTask.setSource(listInfo.get("source"));
                siteTask.setType(listInfo.get("type"));
                siteTask.setUrl(listInfo.get("url"));
            }
            return CodeResult.successResult(null, siteTask);
        } else {
            return CodeResult.failedResult("failed");
        }
    }

    @PutMapping("/siteTask/modify/url")
    public CodeResult modifyByUrl(
            @RequestBody SiteTask siteTask) {
        boolean isSuccess = siteTaskDao.modifyByUrl(siteTask.getUrlMd5(), siteTask);
        return isSuccess ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }

    /**
     * 添加站点
     * @param siteTask 站点配置
     * @return true of false
     */
    @PostMapping("/siteTask")
    public CodeResult add(@RequestBody SiteTask siteTask) {
        if (siteTask.getName() != null) {
            Map<String, String> map = new HashMap<>();
            map.put("name", siteTask.getName());
            map.put("source", siteTask.getSource());
            map.put("type", siteTask.getType());
            map.put("url", siteTask.getUrl());
            String urlMd5 = newsListDao.add(map);
            if (urlMd5 != null) siteTask.setUrlMd5(urlMd5);
            else return CodeResult.failedResult("add error");
        }
        boolean isSuccess = siteTaskDao.add(siteTask);
        return isSuccess ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }

    /**
     * 重置SiteTask的LastTime
     * 相当于让爬虫立即开始执行SiteTask
     * @param urlMd5
     * @return
     */
    @GetMapping("/siteTask/resetLastTime")
    public CodeResult resetLastTime(@RequestParam(value = "urlMd5") String urlMd5) {
        boolean isSuccess = siteTaskDao.resetLastTime(urlMd5);
        return isSuccess ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }

}
