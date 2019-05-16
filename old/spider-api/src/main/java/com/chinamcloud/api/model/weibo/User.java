package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class User {

    private long id;
    @JsonProperty("screen_name")
    private String screenName;
    @JsonProperty("profile_image_url")
    private String profileImageUrl;
    @JsonProperty("profile_url")
    private String profileUrl;
    @JsonProperty("statuses_count")
    private long statusesCount;
    private boolean verified;
    @JsonProperty("verified_type")
    private long verifiedType;
    @JsonProperty("verified_type_ext")
    private long verifiedTypeExt;
    @JsonProperty("verified_reason")
    private String verifiedReason;
    @JsonProperty("close_blue_v")
    private boolean closeBlueV;
    private String description;
    private String gender;
    private long mbtype;
    private long urank;
    private long mbrank;
    @JsonProperty("follow_me")
    private boolean followMe;
    private boolean following;
    @JsonProperty("followers_count")
    private long followersCount;
    @JsonProperty("follow_count")
    private long followCount;
    @JsonProperty("cover_image_phone")
    private String coverImagePhone;
    @JsonProperty("avatar_hd")
    private String avatarHd;
    private boolean like;
    @JsonProperty("like_me")
    private boolean likeMe;
    private Badge badge;

    public void setScreenName(String screenName) {
         this.screenName = screenName;
     }
     public String getScreenName() {
         return screenName;
     }

    public void setProfileImageUrl(String profileImageUrl) {
         this.profileImageUrl = profileImageUrl;
     }
     public String getProfileImageUrl() {
         return profileImageUrl;
     }

    public void setProfileUrl(String profileUrl) {
         this.profileUrl = profileUrl;
     }
     public String getProfileUrl() {
         return profileUrl;
     }

    public void setVerified(boolean verified) {
         this.verified = verified;
     }
     public boolean getVerified() {
         return verified;
     }


    public void setVerifiedReason(String verifiedReason) {
         this.verifiedReason = verifiedReason;
     }
     public String getVerifiedReason() {
         return verifiedReason;
     }

    public void setCloseBlueV(boolean closeBlueV) {
         this.closeBlueV = closeBlueV;
     }
     public boolean getCloseBlueV() {
         return closeBlueV;
     }

    public void setDescription(String description) {
         this.description = description;
     }
     public String getDescription() {
         return description;
     }

    public void setGender(String gender) {
         this.gender = gender;
     }
     public String getGender() {
         return gender;
     }

    public void setFollowMe(boolean followMe) {
         this.followMe = followMe;
     }
     public boolean getFollowMe() {
         return followMe;
     }

    public void setFollowing(boolean following) {
         this.following = following;
     }
     public boolean getFollowing() {
         return following;
     }

    public void setCoverImagePhone(String coverImagePhone) {
         this.coverImagePhone = coverImagePhone;
     }
     public String getCoverImagePhone() {
         return coverImagePhone;
     }

    public void setAvatarHd(String avatarHd) {
         this.avatarHd = avatarHd;
     }
     public String getAvatarHd() {
         return avatarHd;
     }

    public void setLike(boolean like) {
         this.like = like;
     }
     public boolean getLike() {
         return like;
     }

    public void setLikeMe(boolean likeMe) {
         this.likeMe = likeMe;
     }
     public boolean getLikeMe() {
         return likeMe;
     }

    public void setBadge(Badge badge) {
         this.badge = badge;
     }
     public Badge getBadge() {
         return badge;
     }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(long statusesCount) {
        this.statusesCount = statusesCount;
    }

    public long getVerifiedType() {
        return verifiedType;
    }

    public void setVerifiedType(long verifiedType) {
        this.verifiedType = verifiedType;
    }

    public long getVerifiedTypeExt() {
        return verifiedTypeExt;
    }

    public void setVerifiedTypeExt(long verifiedTypeExt) {
        this.verifiedTypeExt = verifiedTypeExt;
    }

    public long getMbtype() {
        return mbtype;
    }

    public void setMbtype(long mbtype) {
        this.mbtype = mbtype;
    }

    public long getUrank() {
        return urank;
    }

    public void setUrank(long urank) {
        this.urank = urank;
    }

    public long getMbrank() {
        return mbrank;
    }

    public void setMbrank(long mbrank) {
        this.mbrank = mbrank;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(long followersCount) {
        this.followersCount = followersCount;
    }

    public long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(long followCount) {
        this.followCount = followCount;
    }
}