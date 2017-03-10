package com.stormphoenix.httpknife.github;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitCommit {
    private String sha;
    private String url;
    private GitUser author;
    private String message;
    private boolean distinct;

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
}
