package com.stormphoenix.httpknife.github;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class GitRelease {
    private String url;
    @SerializedName("assets_url")
    private String assetsUrl;
    @SerializedName("upload_url")
    private String uploadUrl;
    @SerializedName("html_url")
    private String htmlUrl;
    private long id;
    @SerializedName("tag_name")
    private String tagName;
    @SerializedName("target_commitish")
    private String targetCommitish;
    private String name;
    private String draft;
    private GitUser author;
    private boolean prerelease;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("published_at")
    private Date publishedAt;
    //    private List assets;
    @SerializedName("tarball_url")
    private String tarballUrl;
    @SerializedName("zipballUrl")
    private String zipballUrl;
    private String body;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAssetsUrl() {
        return assetsUrl;
    }

    public void setAssetsUrl(String assetsUrl) {
        this.assetsUrl = assetsUrl;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTargetCommitish() {
        return targetCommitish;
    }

    public void setTargetCommitish(String targetCommitish) {
        this.targetCommitish = targetCommitish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public GitUser getAuthor() {
        return author;
    }

    public void setAuthor(GitUser author) {
        this.author = author;
    }

    public boolean isPrerelease() {
        return prerelease;
    }

    public void setPrerelease(boolean prerelease) {
        this.prerelease = prerelease;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTarballUrl() {
        return tarballUrl;
    }

    public void setTarballUrl(String tarballUrl) {
        this.tarballUrl = tarballUrl;
    }

    public String getZipballUrl() {
        return zipballUrl;
    }

    public void setZipballUrl(String zipballUrl) {
        this.zipballUrl = zipballUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
