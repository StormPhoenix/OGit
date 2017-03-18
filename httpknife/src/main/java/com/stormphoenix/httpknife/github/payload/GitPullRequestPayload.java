package com.stormphoenix.httpknife.github.payload;

import com.google.gson.annotations.SerializedName;
import com.stormphoenix.httpknife.github.GitPullRequest;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitPullRequestPayload extends GitPayload {
    private String action;
    private int number;
    @SerializedName("pull_request")
    private GitPullRequest pullRequest;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public GitPullRequest getPullRequest() {
        return pullRequest;
    }

    public void setPullRequest(GitPullRequest pullRequest) {
        this.pullRequest = pullRequest;
    }
}
