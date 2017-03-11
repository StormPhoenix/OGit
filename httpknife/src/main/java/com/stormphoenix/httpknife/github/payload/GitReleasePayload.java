package com.stormphoenix.httpknife.github.payload;

import com.stormphoenix.httpknife.github.GitRelease;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitReleasePayload extends GitPayload {
    private String action;
    private GitRelease release;

    public GitRelease getRelease() {
        return release;
    }

    public void setRelease(GitRelease release) {
        this.release = release;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
