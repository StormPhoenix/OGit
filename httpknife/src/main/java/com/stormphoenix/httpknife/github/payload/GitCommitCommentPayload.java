package com.stormphoenix.httpknife.github.payload;

import com.stormphoenix.httpknife.github.GitCommitComment;

/**
 * Created by StormPhoenix on 17-4-1.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitCommitCommentPayload extends GitPayload {
    private GitCommitComment comment;

    public GitCommitComment getComment() {
        return comment;
    }

    public void setComment(GitCommitComment comment) {
        this.comment = comment;
    }
}
