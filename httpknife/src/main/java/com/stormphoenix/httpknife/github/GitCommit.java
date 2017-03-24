package com.stormphoenix.httpknife.github;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * 包含Commit的所有信息
 *
 * @see GitCommitMessage
 */

public class GitCommit {
    private String sha;
    private GitCommitMessage commit;
    private String url;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("comments_url")
    private String commentsUrl;
    private GitUser author;
    private GitUser committer;
    private List<GitCommitParent> parents;
    private GitCommitStats stats;
    private List<GitCommitFile> files;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public GitCommitMessage getCommit() {
        return commit;
    }

    public void setCommit(GitCommitMessage commit) {
        this.commit = commit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public GitUser getAuthor() {
        return author;
    }

    public void setAuthor(GitUser author) {
        this.author = author;
    }

    public GitUser getCommitter() {
        return committer;
    }

    public void setCommitter(GitUser committer) {
        this.committer = committer;
    }

    public List<GitCommitParent> getParents() {
        return parents;
    }

    public void setParents(List<GitCommitParent> parents) {
        this.parents = parents;
    }

    public GitCommitStats getStats() {
        return stats;
    }

    public void setStats(GitCommitStats stats) {
        this.stats = stats;
    }

    public List<GitCommitFile> getFiles() {
        return files;
    }

    public void setFiles(List<GitCommitFile> files) {
        this.files = files;
    }

    public static class GitCommitParent {
        private String sha;
        private String url;
        @SerializedName("html_url")
        private String htmlUrl;

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

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public void setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
        }
    }
}
