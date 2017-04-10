package com.stormphoenix.httpknife.github;

import java.util.List;

/**
 * Created by StormPhoenix on 17-4-10.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitTrendRepository {
    private String ownerName;
    private String repoName;
    private String repoDesc;
    private String langType;
    private String starNum;
    private String forkNum;
    private String starPerDuration;
    private List<String> contibutorUrl;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoDesc() {
        return repoDesc;
    }

    public void setRepoDesc(String repoDesc) {
        this.repoDesc = repoDesc;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }

    public String getStarNum() {
        return starNum;
    }

    public void setStarNum(String starNum) {
        this.starNum = starNum;
    }

    public String getForkNum() {
        return forkNum;
    }

    public void setForkNum(String forkNum) {
        this.forkNum = forkNum;
    }

    public List<String> getContibutorUrl() {
        return contibutorUrl;
    }

    public void setContibutorUrl(List<String> contibutorUrl) {
        this.contibutorUrl = contibutorUrl;
    }

    public String getStarPerDuration() {
        return starPerDuration;
    }

    public void setStarPerDuration(String starPerDuration) {
        this.starPerDuration = starPerDuration;
    }
}
