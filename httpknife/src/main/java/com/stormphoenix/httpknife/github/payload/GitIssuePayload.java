package com.stormphoenix.httpknife.github.payload;

import com.stormphoenix.httpknife.github.GitIssue;

import java.io.Serializable;

/**
 * Created by Quinn on 10/4/15.
 */
public class GitIssuePayload extends GitPayload{
    private String action;
    private GitIssue issue;

    public String getAction() {
        return this.action;
    }

    public GitIssuePayload setAction(String action) {
        this.action = action;
        return this;
    }

    public GitIssue getIssue() {
        return this.issue;
    }

    public GitIssuePayload setIssue(GitIssue issue) {
        this.issue = issue;
        return this;
    }
}
