package com.stormphoenix.httpknife.github;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by StormPhoenix on 17-3-17.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitNotification {
    private String id;
    private GitRepository repository;
    private GitSubject subject;
    private String reason;
    private boolean unread;
    @SerializedName("update_at")
    private Date updatedAt;
    @SerializedName("last_read_at")
    private Date lastReadAt;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GitRepository getRepository() {
        return repository;
    }

    public void setRepository(GitRepository repository) {
        this.repository = repository;
    }

    public GitSubject getSubject() {
        return subject;
    }

    public void setSubject(GitSubject subject) {
        this.subject = subject;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getLastReadAt() {
        return lastReadAt;
    }

    public void setLastReadAt(Date lastReadAt) {
        this.lastReadAt = lastReadAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
