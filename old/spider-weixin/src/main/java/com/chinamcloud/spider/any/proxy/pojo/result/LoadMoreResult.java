package com.chinamcloud.spider.any.proxy.pojo.result;

import com.alibaba.fastjson.JSON;

public class LoadMoreResult {
    private int ret;
    private String errMsg;
    private int msgCount;
    private int canMsgContinue;
    private MsgList generalMsgList;
    private int nextOffset;
    private int videoCount;
    private int userVideoTab;
    private int realType;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getCanMsgContinue() {
        return canMsgContinue;
    }

    public void setCanMsgContinue(int canMsgContinue) {
        this.canMsgContinue = canMsgContinue;
    }

    public MsgList getGeneralMsgList() {
        return generalMsgList;
    }

    public void setGeneralMsgList(String generalMsgList) {
        this.generalMsgList = JSON.parseObject(generalMsgList, MsgList.class);
    }

    public int getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(int nextOffset) {
        this.nextOffset = nextOffset;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public int getUserVideoTab() {
        return userVideoTab;
    }

    public void setUserVideoTab(int userVideoTab) {
        this.userVideoTab = userVideoTab;
    }

    public int getRealType() {
        return realType;
    }

    public void setRealType(int realType) {
        this.realType = realType;
    }
}
