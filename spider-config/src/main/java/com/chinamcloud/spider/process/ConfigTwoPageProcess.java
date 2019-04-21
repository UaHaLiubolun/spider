package com.chinamcloud.spider.process;

import com.chinamcloud.spider.PageModelTran;
import com.chinamcloud.spider.handle.PageModelExtractor;
import com.chinamcloud.spider.handle.UrlsUtil;
import com.chinamcloud.spider.model.PageModel;
import com.chinamcloud.spider.process.extract.*;
import com.chinamcloud.spider.process.process.OwnProcess;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import java.util.ArrayList;
import java.util.List;


public class ConfigTwoPageProcess implements PageProcessor {

    private Site site;

    public ConfigTwoPageProcess(Site site) {
        this.site = site;
    }

    @Override
    public void process(Page page) {
        List<PageModel> pageModels = PageModelTran.getPageModel(page);
        List<PageModelExtractor> pageModelExtractors = getPageModelExtractors(pageModels, page);
        pageModelExtractors.stream().forEach(pageModelExtractor -> {
            extractLinks(page, pageModelExtractor.getPageModel());
            // 精确爬去 目标页面不爬新Url
            Object process = new Object();
            String ownProcess = pageModelExtractor.getPageModel().getOwnProcess();
            if (ownProcess != null && !ownProcess.equals("")) {
                try {
                    Class c = Class.forName("com.chinamcloud.spider.process.process." + ownProcess);
                    OwnProcess pageProcessor = (OwnProcess) c.newInstance();
                    process = pageProcessor.process(page);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                process = pageModelExtractor.process(page);
            }
            if (process == null || (process instanceof List && ((List) process).size() == 0)) {

            } else {
                UrlsUtil.put(page, process, pageModelExtractor.getPageModel());
            }
        });
        if (page.getResultItems().getAll().size() == 0) {
            page.getResultItems().setSkip(true);
        }
    }

    private List<PageModelExtractor> getPageModelExtractors(List<PageModel> pageModels, Page page) {
        if (pageModels == null) {
            page.setSkip(true);
            return new ArrayList<>();
        }
        List<PageModelExtractor> pageModelExtractors = new ArrayList<>();
        pageModels.stream().forEach(pageModel -> pageModelExtractors.add(new PageModelExtractor(pageModel)) );
        return pageModelExtractors;
    }


    private void extractLinks(Page page, PageModel pageModel) {
        // 只在二级域名是爬取新的Url
        if (page.getRequest().isExtractLinks()) {
            if (pageModel.getTargetUrl() != null && pageModel.getTargetUrl().size() != 0) {
                pageModel.getTargetUrl().forEach(target -> {
                    switchExtractLinks(page, target, pageModel, false);
                });
            }

            if (pageModel.getHelpUrl() != null && pageModel.getHelpUrl().size() != 0) {
                pageModel.getHelpUrl().forEach(target -> {
                    switchExtractLinks(page, target, pageModel, true);
                });
            }

        }
    }

    private void switchExtractLinks(Page page, String value, PageModel pageModel, boolean isExtractLinks) {
        ExtractLinks extractLinks;
        String[] expression = value.split("\\|\\|");
        if (expression.length <= 0) return;
        switch (expression[0]) {
            case "XPath":
                extractLinks = new XpathExtractLinks(page, expression[1], pageModel, isExtractLinks);
                break;
            case "Json":
                extractLinks = new JsonExtractLinks(page, expression[1], pageModel, isExtractLinks);
                break;
            case "Regex":
                extractLinks = new RegexExtractLinks(page, expression[1], pageModel, isExtractLinks);
                break;
            case "RegexAll":
                extractLinks = new RegexAllExtractLinks(page, expression[1], pageModel, isExtractLinks);
                break;
            default:
                extractLinks = new RegexExtractLinks(page, expression[0], pageModel, isExtractLinks);
                break;
        }
        extractLinks.extract();
    }


    @Override
    public Site getSite() {
        return this.site;
    }



}
