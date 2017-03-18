package com.stormphoenix.httpknife.github;

import com.google.gson.annotations.SerializedName;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * 只包含Commit的一部分信息，是GitCommitMessage的局部
 *
 * @see GitCommit
 */

public class GitCommitMessage {
    private String sha;
    // 提交信息的详细地址
    private String url;
    // 提交人
    private GitUser author;
    // 提交附带信息
    private String message;
    private boolean distinct;
    private GitUser committer;
    private GitTree tree;
    @SerializedName("comment_count")
    private int commentCount;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "sha='" + sha + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public GitUser getAuthor() {
        return author;
    }

    public void setAuthor(GitUser author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public GitUser getCommitter() {
        return committer;
    }

    public void setCommitter(GitUser committer) {
        this.committer = committer;
    }

    public GitTree getTree() {
        return tree;
    }

    public void setTree(GitTree tree) {
        this.tree = tree;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
