package com.chinamcloud.api.model.weibo;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class  Author {

    @JsonProperty("object_type")
    private String objectType;
    private long id;
    @JsonProperty("display_name")
    private String displayName;
    private String url;
    @JsonProperty("avatar_large")
    private String avatarLarge;
    @JsonProperty("followers_count")
    private long followersCount;
    @JsonProperty("verified_type")
    private int verifiedType;
    @JsonProperty("verified_type_ext")
    private int verifiedTypeExt;
    @JsonProperty("verified_reason")
    private String verifiedReason;
    private boolean verified;
    @JsonProperty("verified_level")
    private int verifiedLevel;
    public void setObjectType(String objectType) {
         this.objectType = objectType;
     }
     public String getObjectType() {
         return objectType;
     }

    public void setDisplayName(String displayName) {
         this.displayName = displayName;
     }
     public String getDisplayName() {
         return displayName;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

    public void setAvatarLarge(String avatarLarge) {
         this.avatarLarge = avatarLarge;
     }
     public String getAvatarLarge() {
         return avatarLarge;
     }

    public void setVerifiedType(int verifiedType) {
         this.verifiedType = verifiedType;
     }
     public int getVerifiedType() {
         return verifiedType;
     }

    public void setVerifiedTypeExt(int verifiedTypeExt) {
         this.verifiedTypeExt = verifiedTypeExt;
     }
     public int getVerifiedTypeExt() {
         return verifiedTypeExt;
     }

    public void setVerifiedReason(String verifiedReason) {
         this.verifiedReason = verifiedReason;
     }
     public String getVerifiedReason() {
         return verifiedReason;
     }

    public void setVerified(boolean verified) {
         this.verified = verified;
     }
     public boolean getVerified() {
         return verified;
     }

    public void setVerifiedLevel(int verifiedLevel) {
         this.verifiedLevel = verifiedLevel;
     }
     public int getVerifiedLevel() {
         return verifiedLevel;
     }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(long followersCount) {
        this.followersCount = followersCount;
    }
}