package com.chinamcloud.spider.model;


import java.util.List;

public class PageModel {

    // only regex
    private List<String> targetUrl;
    // only regex
    private List<String> helpUrl;

    private String ownProcess;

    // 如果一个页面要抽取多条记录
    private Extract multiExtract;

    private List<Extract> extracts;

    public String getOwnProcess() {
        return ownProcess;
    }

    public void setOwnProcess(String ownProcess) {
        this.ownProcess = ownProcess;
    }

    public List<String> getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(List<String> targetUrl) {
        this.targetUrl = targetUrl;
    }

    public List<String> getHelpUrl() {
        return helpUrl;
    }

    public void setHelpUrl(List<String> helpUrl) {
        this.helpUrl = helpUrl;
    }

    public Extract getMultiExtract() {
        return multiExtract;
    }

    public void setMultiExtract(Extract multiExtract) {
        this.multiExtract = multiExtract;
    }

    public List<Extract> getExtracts() {
        return extracts;
    }

    public void setExtracts(List<Extract> extracts) {
        this.extracts = extracts;
    }
}
