package com.stormphoenix.httpknife.github;

import com.google.gson.annotations.SerializedName;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitOrg {
    /**
     * login : github
     * id : 1
     * url : https://api.github.com/orgs/github
     * reposUrl : https://api.github.com/orgs/github/repos
     * eventsUrl : https://api.github.com/orgs/github/events
     * hooksUrl : https://api.github.com/orgs/github/hooks
     * issuesUrl : https://api.github.com/orgs/github/issues
     * membersUrl : https://api.github.com/orgs/github/members{/member}
     * publicMembersUrl : https://api.github.com/orgs/github/public_members{/member}
     * avatarUrl : https://github.com/images/error/octocat_happy.gif
     * description : A great organization
     */

    private String login;
    private int id;
    private String url;
    @SerializedName("repos_url")
    private String reposUrl;
    @SerializedName("events_url")
    private String eventsUrl;
    @SerializedName("hooks_url")
    private String hooksUrl;
    @SerializedName("issues_url")
    private String issuesUrl;
    @SerializedName("members_url")
    private String membersUrl;
    @SerializedName("public_members_url")
    private String publicMembersUrl;
    @SerializedName("avatar_url")
    private String avatarUrl;
    private String description;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    public String getHooksUrl() {
        return hooksUrl;
    }

    public void setHooksUrl(String hooksUrl) {
        this.hooksUrl = hooksUrl;
    }

    public String getIssuesUrl() {
        return issuesUrl;
    }

    public void setIssuesUrl(String issuesUrl) {
        this.issuesUrl = issuesUrl;
    }

    public String getMembersUrl() {
        return membersUrl;
    }

    public void setMembersUrl(String membersUrl) {
        this.membersUrl = membersUrl;
    }

    public String getPublicMembersUrl() {
        return publicMembersUrl;
    }

    public void setPublicMembersUrl(String publicMembersUrl) {
        this.publicMembersUrl = publicMembersUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
