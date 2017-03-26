package com.stormphoenix.httpknife.github.payload;

import com.stormphoenix.httpknife.GitIssueComment;
import com.stormphoenix.httpknife.github.GitIssue;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitIssueCommentPayload extends GitPayload {
    private String action;
    private GitIssue issue;
    private GitIssueComment comment;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public GitIssue getIssue() {
        return issue;
    }

    public void setIssue(GitIssue issue) {
        this.issue = issue;
    }

    public GitIssueComment getComment() {
        return comment;
    }

    public void setComment(GitIssueComment comment) {
        this.comment = comment;
    }
}
