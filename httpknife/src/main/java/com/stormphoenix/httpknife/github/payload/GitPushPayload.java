package com.stormphoenix.httpknife.github.payload;

import com.google.gson.annotations.SerializedName;
import com.stormphoenix.httpknife.github.GitCommitMessage;

import java.util.Date;
import java.util.List;

/**
 * Created by StormPhoenix on 17-3-6.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitPushPayload extends GitPayload {
    @SerializedName("push_id")
    private String pushId;
    private long size;
    @SerializedName("distinct_size")
    private String distinctSize;
    private String ref;
    private String head;
    private String before;
    private List<GitCommitMessage> commits;
    @SerializedName("public")
    private boolean publik;
    @SerializedName("created_at")
    private Date createdAt;

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDistinctSize() {
        return distinctSize;
    }

    public void setDistinctSize(String distinctSize) {
        this.distinctSize = distinctSize;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public List<GitCommitMessage> getCommits() {
        return commits;
    }

    public void setCommits(List<GitCommitMessage> commits) {
        this.commits = commits;
    }

    public boolean isPublik() {
        return publik;
    }

    public void setPublik(boolean publik) {
        this.publik = publik;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
