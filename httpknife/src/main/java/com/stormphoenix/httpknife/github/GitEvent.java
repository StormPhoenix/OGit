package com.stormphoenix.httpknife.github;

import com.google.gson.annotations.SerializedName;
import com.stormphoenix.httpknife.github.payload.GitPayload;

import java.util.Date;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitEvent {
    public static final String GIT_RELEASE_EVENT = "ReleaseEvent";
    public static final String GIT_WATCH_EVENT = "WatchEvent";
    public static final String GIT_PUSH_EVENT = "PushEvent";
    public final static String GIT_FORK_EVENT = "ForkEvent";
    public final static String GIT_CREATE_EVENT = "CreateEvent";
    public final static String GIT_PULL_REQUEST_EVENT = "PullRequestEvent";
    public final static String GIT_MEMBER_EVENT = "MemberEvent";
    public final static String GIT_ISSUES_EVENT = "IssuesEvent";
    public final static String GIT_PUBLIC_EVENT = "PublicEvent";
    public final static String GIT_ISSUE_COMMENT_EVENT = "IssueCommentEvent";
    public final static String GIT_COMMIT_COMMENT_EVENT = "CommitCommentEvent";


    private String id;
    private String type;
    private GitUser actor;
    private GitRepository repo;
    @SerializedName("created_at")
    private Date createdDate;
    // payload需要根据type的类型进行转型
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
