package com.stormphoenix.httpknife.github.payload;

import com.stormphoenix.httpknife.github.GitUser;

import java.io.Serializable;

/**
 * Created by Quinn on 15/10/2.
 */
public class GitMemberPayload extends GitPayload {
    private GitUser member;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public GitUser getMember() {
        return member;
    }

    public void setMember(GitUser member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "GitMemberPayload{" +
                "action='" + action + '\'' +
                ", member=" + member +
                '}';
    }
}
