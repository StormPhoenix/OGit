package com.stormphoenix.httpknife.github;

import com.google.gson.annotations.SerializedName;

/**
 * Created by StormPhoenix on 17-3-17.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitSubject {
    public static final String SUBJECT_TYPE_ISSUE = "Issue";
    public static final String SUBJECT_TYPE_RELEASE = "Release";
    public static final String SUBJECT_TYPE_PULL_REQUEST = "PullRequest";
    public static final String SUBJECT_TYPE_COMMIT = "Commit";

    private String title;
    private String url;
    @SerializedName("latest_comment_url")
    private String latestCommentUrl;
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatestCommentUrl() {
        return latestCommentUrl;
    }

    public void setLatestCommentUrl(String latestCommentUrl) {
        this.latestCommentUrl = latestCommentUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
