package com.stormphoenix.httpknife.github;

import java.util.Date;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitEvent {
    public static final int GIT_EVENT_CREATED = 0x0001;
    public static final int GIT_EVENT_STARRED = 0x0002;
    public static final int GIT_EVENT_FORKED = 0x0004;
    public static final int GIT_EVENT_PUSH = 0x0008;

    private String id;
    private String type;
    private GitUser actor;
    private GitRepository repo;
    private Date createdDate;
    private GitPayload payload;
    private GitUser org;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GitUser getActor() {
        return actor;
    }

    public void setActor(GitUser actor) {
        this.actor = actor;
    }

    public GitRepository getRepo() {
        return repo;
    }

    public void setRepo(GitRepository repo) {
        this.repo = repo;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public GitPayload getPayload() {
        return payload;
    }

    public void setPayload(GitPayload payload) {
        this.payload = payload;
    }

    public GitUser getOrg() {
        return org;
    }

    public void setOrg(GitUser org) {
        this.org = org;
    }
}
