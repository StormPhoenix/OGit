package com.stormphoenix.httpknife.github;

/**
 * Created by StormPhoenix on 17-2-27.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitCommit {
    private String sha;
    private String url;

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
}
