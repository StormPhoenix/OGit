package com.stormphoenix.httpknife.github;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitOrganization {
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
    public static final String TYPE_ORG = "Organization";

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
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    @SerializedName("public_repos")
    private int publicRepos;
    @SerializedName("public_gists")
    private int publicGists;
    private int followers;
    private int following;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("updated_at")
    private Date updatedAt;
    private String type;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(int publicRepos) {
        this.publicRepos = publicRepos;
    }

    public int getPublicGists() {
        return publicGists;
    }

    public void setPublicGists(int publicGists) {
        this.publicGists = publicGists;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
