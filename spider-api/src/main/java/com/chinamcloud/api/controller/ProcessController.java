package com.chinamcloud.api.controller;

import com.chinamcloud.api.CodeResult;
import com.chinamcloud.api.dao.PageModelModuleDao;
import com.chinamcloud.api.model.PageModelModule;
import com.chinamcloud.spider.handle.PageModelExtractor;
import com.chinamcloud.spider.model.PageModel;
import com.chinamcloud.spider.process.ConfigTwoPageProcess;
import com.chinamcloud.spider.process.extract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class ProcessController {

    private Downloader downloader;

    @Autowired
    private PageModelModuleDao pageModelModuleDao;

    {
        downloader = new HttpClientDownloader();
    }

    @GetMapping("/process/extractLinks")
    public CodeResult extractLinks(
            @RequestParam(value = "url") String url,
            @RequestParam(value = "expression") String expression,
            @RequestParam(value = "charset") String charset
    ) {
        url = URLDecoder.decode(url);
        Page page = downloader.download(new Request(url), Site.me().setCharset(charset).toTask());
        String[] s = expression.split(",");
        List<String> list = new LinkedList<>();
        for (String exp:
             s) {
            ExtractLinks extractLinks;
            String[] expressions = exp.replace(" ", "+").split("\\|\\|");
            if (expressions.length <= 1) return CodeResult.failedResult("表达式错误");
            switch (expressions[0]) {
                case "XPath":
                    extractLinks = new XpathExtractLinks();
                    break;
                case "Json":
                    extractLinks = new JsonExtractLinks();
                    break;
                case "Regex":
                    extractLinks = new RegexExtractLinks();
                    break;
                case "RegexAll":
                    extractLinks = new RegexAllExtractLinks();
                    break;
                default:
                    extractLinks = new RegexExtractLinks();
                    break;
            }
            List<String> strings = extractLinks.extractUrl(page, expressions[1]);
            if (strings != null) {
                list.addAll(strings);
            }
        }
        return CodeResult.successResult("success", list);
    }

    @GetMapping("/process/chosePageModule")
    public CodeResult chosePageModule(@RequestParam(value = "url") String url,
                                      @RequestParam(value = "charset") String charset) {
        url = URLDecoder.decode(url);
        Page page = downloader.download(new Request(url), Site.me().setCharset(charset).toTask());
        page.getRequest().setExtractLinks(false);
        page.getRequest().setTest(true);
        List<PageModelModule> pageModelModules = getModels(url);
        List<Map<String, Object>> maps = new ArrayList<>();
        for (PageModelModule pageModel:
             pageModelModules) {
            Map<String, Object> result = new HashMap<>();
            PageModelExtractor pageModelExtractor = new PageModelExtractor(pageModel.getPageModel());
            Object o = pageModelExtractor.process(page);
            result.put("data", o);
            result.put("name", pageModel.getName());
            result.put("pageModel", pageModel.getPageModel());
            maps.add(result);
        }
        return CodeResult.successResult("success", maps);
    }

    @PostMapping("/process/testPageModel")
    public CodeResult testPageModel(@RequestParam(value = "url") String url,
                                    @RequestParam(value = "charset") String charset,
                                    @RequestBody PageModel pageModel) {
        PageModelExtractor pageModelExtractor = new PageModelExtractor(pageModel);
        Page page = downloader.download(new Request(url), Site.me().setCharset(charset).toTask());
        page.getRequest().setExtractLinks(false);
        page.getRequest().setTest(true);
        Object o = pageModelExtractor.process(page);
        return CodeResult.successResult("success", o);
    }

    private List<PageModelModule> getModels(String url) {
        List<PageModelModule> pageModelModules = pageModelModuleDao.getAll();
        List<PageModelModule> matcherModel = pageModelModules.stream().filter(
                pageModelModule -> {
                    List<String> urls = pageModelModule.getPageModel().getTargetUrl();
                    for (String s:
                         urls) {
                        String regex = getSite(s);
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(url);
                        if (matcher.find()) {
                            return true;
                        }
                    }
                    return false;
                }).collect(Collectors.toList());
        return matcherModel;
    }

    private String getSite(String url) {
        String[] urls = url.split("\\|\\|");
        String u;
        if (urls.length == 1) {
            u =  UrlUtils.getDomain(url);
        } else {
            u =  UrlUtils.getDomain(urls[1]);
        }
        u = u.replace("www.", "");
        return u;
    }
}
