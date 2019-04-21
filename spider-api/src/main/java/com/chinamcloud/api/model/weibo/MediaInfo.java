package com.chinamcloud.api.model.weibo;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-12-24 10:57:5
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class MediaInfo {

    @JsonProperty("video_orientation")
    private String videoOrientation;
    private String name;
    private int duration;
    @JsonProperty("stream_url")
    private String streamUrl;
    @JsonProperty("stream_url_hd")
    private String streamUrlHd;
    @JsonProperty("h5_url")
    private String h5Url;
    @JsonProperty("mp4_sd_url")
    private String mp4SdUrl;
    @JsonProperty("mp4_hd_url")
    private String mp4HdUrl;
    @JsonProperty("h265_mp4_hd")
    private String h265Mp4Hd;
    @JsonProperty("h265_mp4_ld")
    private String h265Mp4Ld;
    @JsonProperty("inch_4_mp4_hd")
    private String inch4Mp4Hd;
    @JsonProperty("inch_5_mp4_hd")
    private String inch5Mp4Hd;
    @JsonProperty("inch_5_5_mp4_hd")
    private String inch55Mp4Hd;
    @JsonProperty("mp4_720p_mp4")
    private String mp4720pMp4;
    @JsonProperty("hevc_mp4_720p")
    private String hevcMp4720p;
    @JsonProperty("prefetch_type")
    private int prefetchType;
    @JsonProperty("prefetch_size")
    private int prefetchSize;
    @JsonProperty("act_status")
    private int actStatus;
    private String protocol;
    @JsonProperty("media_id")
    private String mediaId;
    @JsonProperty("origin_total_bitrate")
    private int originTotalBitrate;
    @JsonProperty("next_title")
    private String nextTitle;
    @JsonProperty("video_details")
    private List<VideoDetails> videoDetails;
    @JsonProperty("play_completion_actions")
    private List<PlayCompletionActions> playCompletionActions;
    @JsonProperty("video_publish_time")
    private int videoPublishTime;
    @JsonProperty("play_loop_type")
    private int playLoopType;
    private List<Titles> titles;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("extra_info")
    private ExtraInfo extraInfo;
    @JsonProperty("has_recommend_video")
    private int hasRecommendVideo;
    @JsonProperty("online_users")
    private String onlineUsers;
    @JsonProperty("online_users_number")
    private int onlineUsersNumber;
    @JsonProperty("is_keep_current_mblog")
    private int isKeepCurrentMblog;
    public void setVideoOrientation(String videoOrientation) {
         this.videoOrientation = videoOrientation;
     }
     public String getVideoOrientation() {
         return videoOrientation;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setDuration(int duration) {
         this.duration = duration;
     }
     public int getDuration() {
         return duration;
     }

    public void setStreamUrl(String streamUrl) {
         this.streamUrl = streamUrl;
     }
     public String getStreamUrl() {
         return streamUrl;
     }

    public void setStreamUrlHd(String streamUrlHd) {
         this.streamUrlHd = streamUrlHd;
     }
     public String getStreamUrlHd() {
         return streamUrlHd;
     }

    public void setH5Url(String h5Url) {
         this.h5Url = h5Url;
     }
     public String getH5Url() {
         return h5Url;
     }

    public void setMp4SdUrl(String mp4SdUrl) {
         this.mp4SdUrl = mp4SdUrl;
     }
     public String getMp4SdUrl() {
         return mp4SdUrl;
     }

    public void setMp4HdUrl(String mp4HdUrl) {
         this.mp4HdUrl = mp4HdUrl;
     }
     public String getMp4HdUrl() {
         return mp4HdUrl;
     }

    public void setH265Mp4Hd(String h265Mp4Hd) {
         this.h265Mp4Hd = h265Mp4Hd;
     }
     public String getH265Mp4Hd() {
         return h265Mp4Hd;
     }

    public void setH265Mp4Ld(String h265Mp4Ld) {
         this.h265Mp4Ld = h265Mp4Ld;
     }
     public String getH265Mp4Ld() {
         return h265Mp4Ld;
     }

    public void setInch4Mp4Hd(String inch4Mp4Hd) {
         this.inch4Mp4Hd = inch4Mp4Hd;
     }
     public String getInch4Mp4Hd() {
         return inch4Mp4Hd;
     }

    public void setInch5Mp4Hd(String inch5Mp4Hd) {
         this.inch5Mp4Hd = inch5Mp4Hd;
     }
     public String getInch5Mp4Hd() {
         return inch5Mp4Hd;
     }

    public void setInch55Mp4Hd(String inch55Mp4Hd) {
         this.inch55Mp4Hd = inch55Mp4Hd;
     }
     public String getInch55Mp4Hd() {
         return inch55Mp4Hd;
     }

    public void setMp4720pMp4(String mp4720pMp4) {
         this.mp4720pMp4 = mp4720pMp4;
     }
     public String getMp4720pMp4() {
         return mp4720pMp4;
     }

    public void setHevcMp4720p(String hevcMp4720p) {
         this.hevcMp4720p = hevcMp4720p;
     }
     public String getHevcMp4720p() {
         return hevcMp4720p;
     }

    public void setPrefetchType(int prefetchType) {
         this.prefetchType = prefetchType;
     }
     public int getPrefetchType() {
         return prefetchType;
     }

    public void setPrefetchSize(int prefetchSize) {
         this.prefetchSize = prefetchSize;
     }
     public int getPrefetchSize() {
         return prefetchSize;
     }

    public void setActStatus(int actStatus) {
         this.actStatus = actStatus;
     }
     public int getActStatus() {
         return actStatus;
     }

    public void setProtocol(String protocol) {
         this.protocol = protocol;
     }
     public String getProtocol() {
         return protocol;
     }

    public void setMediaId(String mediaId) {
         this.mediaId = mediaId;
     }
     public String getMediaId() {
         return mediaId;
     }

    public void setOriginTotalBitrate(int originTotalBitrate) {
         this.originTotalBitrate = originTotalBitrate;
     }
     public int getOriginTotalBitrate() {
         return originTotalBitrate;
     }

    public void setNextTitle(String nextTitle) {
         this.nextTitle = nextTitle;
     }
     public String getNextTitle() {
         return nextTitle;
     }

    public void setVideoDetails(List<VideoDetails> videoDetails) {
         this.videoDetails = videoDetails;
     }
     public List<VideoDetails> getVideoDetails() {
         return videoDetails;
     }

    public void setPlayCompletionActions(List<PlayCompletionActions> playCompletionActions) {
         this.playCompletionActions = playCompletionActions;
     }
     public List<PlayCompletionActions> getPlayCompletionActions() {
         return playCompletionActions;
     }

    public void setVideoPublishTime(int videoPublishTime) {
         this.videoPublishTime = videoPublishTime;
     }
     public int getVideoPublishTime() {
         return videoPublishTime;
     }

    public void setPlayLoopType(int playLoopType) {
         this.playLoopType = playLoopType;
     }
     public int getPlayLoopType() {
         return playLoopType;
     }

    public void setTitles(List<Titles> titles) {
         this.titles = titles;
     }
     public List<Titles> getTitles() {
         return titles;
     }

    public void setAuthorName(String authorName) {
         this.authorName = authorName;
     }
     public String getAuthorName() {
         return authorName;
     }

    public void setExtraInfo(ExtraInfo extraInfo) {
         this.extraInfo = extraInfo;
     }
     public ExtraInfo getExtraInfo() {
         return extraInfo;
     }

    public void setHasRecommendVideo(int hasRecommendVideo) {
         this.hasRecommendVideo = hasRecommendVideo;
     }
     public int getHasRecommendVideo() {
         return hasRecommendVideo;
     }

    public void setOnlineUsers(String onlineUsers) {
         this.onlineUsers = onlineUsers;
     }
     public String getOnlineUsers() {
         return onlineUsers;
     }

    public void setOnlineUsersNumber(int onlineUsersNumber) {
         this.onlineUsersNumber = onlineUsersNumber;
     }
     public int getOnlineUsersNumber() {
         return onlineUsersNumber;
     }

    public void setIsKeepCurrentMblog(int isKeepCurrentMblog) {
         this.isKeepCurrentMblog = isKeepCurrentMblog;
     }
     public int getIsKeepCurrentMblog() {
         return isKeepCurrentMblog;
     }

}