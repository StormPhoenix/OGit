package com.stormphoenix.httpknife.github.payload;

import com.stormphoenix.httpknife.GitComment;
import com.stormphoenix.httpknife.github.GitIssue;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitIssueCommentPayload extends GitPayload {
    private String action;
    private GitIssue issue;
    private GitComment comment;

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

    public GitComment getComment() {
        return comment;
    }

    public void setComment(GitComment comment) {
        this.comment = comment;
    }
}
