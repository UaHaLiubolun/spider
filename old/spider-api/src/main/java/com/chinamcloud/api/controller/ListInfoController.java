package com.chinamcloud.api.controller;
import com.chinamcloud.api.CodeResult;
import com.chinamcloud.api.dao.ListInfoDao;
import com.chinamcloud.api.dao.NewsListDao;
import com.chinamcloud.api.model.ListInfo;
import com.chinamcloud.spider.dao.SiteTaskDao;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin
public class ListInfoController {

    @Resource(name = "listInfoDao")
    private ListInfoDao listInfoDao;

    @Resource(name = "newsListDao")
    private NewsListDao newsListDao;


    private SiteTaskDao siteTaskDao;

    {
        siteTaskDao = new SiteTaskDao();
    }

    /**
     * 删除站点
     * @param url  站点Url
     * @return true or false
     */
    @DeleteMapping("listInfo")
    public CodeResult deleteListInfo(@RequestParam(value = "url") String url) {
        listInfoDao.deleteByUrl(url);
        siteTaskDao.deleteByUrlMd5(url);
        boolean isSuccess = newsListDao.delete(url);
        return isSuccess ? CodeResult.successResult() : CodeResult.failedResult("failed");
    }

}
