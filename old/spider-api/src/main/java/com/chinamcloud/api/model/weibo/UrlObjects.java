package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class UrlObjects {

    @JsonProperty("url_ori")
    private String urlOri;
    @JsonProperty("object_id")
    private String objectId;
    private Info info;
    private MObject object;
    @JsonProperty("like_count")
    private long likeCount;
    @JsonProperty("play_count")
    private long playCount;
    @JsonProperty("isActionType")
    private boolean isactiontype;
    @JsonProperty("follower_count")
    private long followerCount;
    @JsonProperty("asso_like_count")
    private long assoLikeCount;
    @JsonProperty("card_info_un_integrity")
    private boolean cardInfoUnIntegrity;
    @JsonProperty("super_topic_status_count")
    private long superTopicStatusCount;
    @JsonProperty("super_topic_photo_count")
    private long superTopicPhotoCount;
    @JsonProperty("search_topic_count")
    private long searchTopicCount;
    @JsonProperty("search_topic_read_count")
    private long searchTopicReadCount;
    @JsonProperty("is_follow_object_author")
    private boolean isFollowObjectAuthor;
    public void setUrlOri(String urlOri) {
         this.urlOri = urlOri;
     }
     public String getUrlOri() {
         return urlOri;
     }

    public void setObjectId(String objectId) {
         this.objectId = objectId;
     }
     public String getObjectId() {
         return objectId;
     }

    public void setInfo(Info info) {
         this.info = info;
     }
     public Info getInfo() {
         return info;
     }

    public void setObject(MObject object) {
         this.object = object;
     }
     public MObject getObject() {
         return object;
     }

    public void setIsactiontype(boolean isactiontype) {
         this.isactiontype = isactiontype;
     }
     public boolean getIsactiontype() {
         return isactiontype;
     }
    public void setCardInfoUnIntegrity(boolean cardInfoUnIntegrity) {
         this.cardInfoUnIntegrity = cardInfoUnIntegrity;
     }
     public boolean getCardInfoUnIntegrity() {
         return cardInfoUnIntegrity;
     }

    public void setSuperTopicStatusCount(int superTopicStatusCount) {
         this.superTopicStatusCount = superTopicStatusCount;
     }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(long playCount) {
        this.playCount = playCount;
    }

    public long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(long followerCount) {
        this.followerCount = followerCount;
    }

    public long getAssoLikeCount() {
        return assoLikeCount;
    }

    public void setAssoLikeCount(long assoLikeCount) {
        this.assoLikeCount = assoLikeCount;
    }

    public long getSuperTopicStatusCount() {
        return superTopicStatusCount;
    }

    public void setSuperTopicStatusCount(long superTopicStatusCount) {
        this.superTopicStatusCount = superTopicStatusCount;
    }

    public long getSuperTopicPhotoCount() {
        return superTopicPhotoCount;
    }

    public void setSuperTopicPhotoCount(long superTopicPhotoCount) {
        this.superTopicPhotoCount = superTopicPhotoCount;
    }

    public long getSearchTopicCount() {
        return searchTopicCount;
    }

    public void setSearchTopicCount(long searchTopicCount) {
        this.searchTopicCount = searchTopicCount;
    }

    public long getSearchTopicReadCount() {
        return searchTopicReadCount;
    }

    public void setSearchTopicReadCount(long searchTopicReadCount) {
        this.searchTopicReadCount = searchTopicReadCount;
    }

    public void setIsFollowObjectAuthor(boolean isFollowObjectAuthor) {
         this.isFollowObjectAuthor = isFollowObjectAuthor;
     }
     public boolean getIsFollowObjectAuthor() {
         return isFollowObjectAuthor;
     }

}