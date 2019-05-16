package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Mblog {

    @JsonProperty("created_at")
    private String createdAt;
    private String id;
    private String idstr;
    private String mid;
    @JsonProperty("edit_count")
    private int editCount;
    @JsonProperty("can_edit")
    private boolean canEdit;
    @JsonProperty("edit_at")
    private String editAt;
    @JsonProperty("show_additional_indication")
    private int showAdditionalIndication;
    private String text;
    @JsonProperty("textLength")
    private int textlength;
    private String source;
    private boolean favorited;
    @JsonProperty("is_paid")
    private boolean isPaid;
    @JsonProperty("mblog_vip_type")
    private int mblogVipType;
    private User user;
    @JsonProperty("reposts_count")
    private long repostsCount;
    @JsonProperty("comments_count")
    private long commentsCount;
    @JsonProperty("attitudes_count")
    private long attitudesCount;
    @JsonProperty("pending_approval_count")
    private long pendingApprovalCount;
    @JsonProperty("isLongText")
    private boolean islongtext;
    @JsonProperty("longText")
    private Longtext longtext;
    @JsonProperty("reward_exhibition_type")
    private int rewardExhibitionType;
    @JsonProperty("hide_flag")
    private int hideFlag;
    private Visible visible;
    private int mblogtype;
    private String rid;
    @JsonProperty("more_info_type")
    private int moreInfoType;
    @JsonProperty("extern_safe")
    private int externSafe;
    @JsonProperty("content_auth")
    private int contentAuth;
    private int status;
    @JsonProperty("digit_attr")
    private String digitAttr;
    @JsonProperty("kwfilter_pass")
    private String kwfilterPass;
    private String itemid;
    @JsonProperty("weibo_position")
    private int weiboPosition;
    @JsonProperty("show_attitude_bar")
    private int showAttitudeBar;
    @JsonProperty("obj_ext")
    private String objExt;
    @JsonProperty("page_info")
    private PageInfo pageInfo;
    private List<Pics> pics;
    private String bid;
    public void setCreatedAt(String createdAt) {
         this.createdAt = createdAt;
     }
     public String getCreatedAt() {
         return createdAt;
     }

    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setIdstr(String idstr) {
         this.idstr = idstr;
     }
     public String getIdstr() {
         return idstr;
     }

    public void setMid(String mid) {
         this.mid = mid;
     }
     public String getMid() {
         return mid;
     }

    public void setEditCount(int editCount) {
         this.editCount = editCount;
     }
     public int getEditCount() {
         return editCount;
     }

    public void setCanEdit(boolean canEdit) {
         this.canEdit = canEdit;
     }
     public boolean getCanEdit() {
         return canEdit;
     }

    public void setEditAt(String editAt) {
         this.editAt = editAt;
     }
     public String getEditAt() {
         return editAt;
     }

    public void setShowAdditionalIndication(int showAdditionalIndication) {
         this.showAdditionalIndication = showAdditionalIndication;
     }
     public int getShowAdditionalIndication() {
         return showAdditionalIndication;
     }

    public void setText(String text) {
         this.text = text;
     }
     public String getText() {
         return text;
     }

    public void setTextlength(int textlength) {
         this.textlength = textlength;
     }
     public int getTextlength() {
         return textlength;
     }

    public void setSource(String source) {
         this.source = source;
     }
     public String getSource() {
         return source;
     }

    public void setFavorited(boolean favorited) {
         this.favorited = favorited;
     }
     public boolean getFavorited() {
         return favorited;
     }

    public void setIsPaid(boolean isPaid) {
         this.isPaid = isPaid;
     }
     public boolean getIsPaid() {
         return isPaid;
     }

    public void setMblogVipType(int mblogVipType) {
         this.mblogVipType = mblogVipType;
     }
     public int getMblogVipType() {
         return mblogVipType;
     }

    public void setUser(User user) {
         this.user = user;
     }
     public User getUser() {
         return user;
     }

    public void setIslongtext(boolean islongtext) {
         this.islongtext = islongtext;
     }
     public boolean getIslongtext() {
         return islongtext;
     }

    public void setLongtext(Longtext longtext) {
         this.longtext = longtext;
     }
     public Longtext getLongtext() {
         return longtext;
     }

    public void setRewardExhibitionType(int rewardExhibitionType) {
         this.rewardExhibitionType = rewardExhibitionType;
     }
     public int getRewardExhibitionType() {
         return rewardExhibitionType;
     }

    public void setHideFlag(int hideFlag) {
         this.hideFlag = hideFlag;
     }
     public int getHideFlag() {
         return hideFlag;
     }

    public void setVisible(Visible visible) {
         this.visible = visible;
     }
     public Visible getVisible() {
         return visible;
     }

    public void setMblogtype(int mblogtype) {
         this.mblogtype = mblogtype;
     }
     public int getMblogtype() {
         return mblogtype;
     }

    public void setRid(String rid) {
         this.rid = rid;
     }
     public String getRid() {
         return rid;
     }

    public void setMoreInfoType(int moreInfoType) {
         this.moreInfoType = moreInfoType;
     }
     public int getMoreInfoType() {
         return moreInfoType;
     }

    public void setExternSafe(int externSafe) {
         this.externSafe = externSafe;
     }
     public int getExternSafe() {
         return externSafe;
     }

    public void setContentAuth(int contentAuth) {
         this.contentAuth = contentAuth;
     }
     public int getContentAuth() {
         return contentAuth;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setDigitAttr(String digitAttr) {
         this.digitAttr = digitAttr;
     }
     public String getDigitAttr() {
         return digitAttr;
     }

    public void setKwfilterPass(String kwfilterPass) {
         this.kwfilterPass = kwfilterPass;
     }
     public String getKwfilterPass() {
         return kwfilterPass;
     }

    public void setItemid(String itemid) {
         this.itemid = itemid;
     }
     public String getItemid() {
         return itemid;
     }

    public void setWeiboPosition(int weiboPosition) {
         this.weiboPosition = weiboPosition;
     }
     public int getWeiboPosition() {
         return weiboPosition;
     }

    public void setShowAttitudeBar(int showAttitudeBar) {
         this.showAttitudeBar = showAttitudeBar;
     }
     public int getShowAttitudeBar() {
         return showAttitudeBar;
     }

    public void setObjExt(String objExt) {
         this.objExt = objExt;
     }
     public String getObjExt() {
         return objExt;
     }

    public void setPageInfo(PageInfo pageInfo) {
         this.pageInfo = pageInfo;
     }
     public PageInfo getPageInfo() {
         return pageInfo;
     }

    public void setBid(String bid) {
         this.bid = bid;
     }
     public String getBid() {
         return bid;
     }


    public List<Pics> getPics() {
        return pics;
    }

    public void setPics(List<Pics> pics) {
        this.pics = pics;
    }

    public long getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(long repostsCount) {
        this.repostsCount = repostsCount;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public long getAttitudesCount() {
        return attitudesCount;
    }

    public void setAttitudesCount(long attitudesCount) {
        this.attitudesCount = attitudesCount;
    }

    public long getPendingApprovalCount() {
        return pendingApprovalCount;
    }

    public void setPendingApprovalCount(long pendingApprovalCount) {
        this.pendingApprovalCount = pendingApprovalCount;
    }
}