package com.stormphoenix.httpknife.github;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitUser implements Serializable {
    private String login;
    private String id;
    private String avatarUrl;
    private String gravatarId;
    private String url;
    private String membersUrl;
    private String htmlUrl;
    private String followersUrl;
    private String followingUrl;
    private String gistsUrl;
    private String starredUrl;
    private String subscriptionsUrl;
    private String organizationsUrl;
    private String reposUrl;
    private String eventsUrl;
    private String receivedEventsUrl;
    private String type;
    private String siteAdmin;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private boolean hireable;
    private String bio;
    private int publicRepos;
    private int publicGists;
    private int followers;
    private int following;
    private Date createdAt;
    private Date updatedAt;
    private int totalPrivateRepos;
    private int ownedPrivateRepos;
    private int privateGists;
    private int diskUsage;
    private int collaborators;
    private Plan plan;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    private static class Plan implements Serializable{
        private String name;
        private int space;
        private int privateRepos;
        private int collaborators;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getSpace() {
            return space;
        }
        public void setSpace(int space) {
            this.space = space;
        }
        public int getPrivateRepos() {
            return privateRepos;
        }
        public void setPrivateRepos(int privateRepos) {
            this.privateRepos = privateRepos;
        }
        public int getCollaborators() {
            return collaborators;
        }
        public void setCollaborators(int collaborators) {
            this.collaborators = collaborators;
        }

        @Override
        public String toString() {
            return "Plan [name=" + name + ", space=" + space
                    + ", privateRepos=" + privateRepos + ", collaborators="
                    + collaborators + "]";
        }
    }
}
