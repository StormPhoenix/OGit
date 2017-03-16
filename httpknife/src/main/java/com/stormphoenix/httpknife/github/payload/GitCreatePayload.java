package com.stormphoenix.httpknife.github.payload;

import com.google.gson.annotations.SerializedName;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitCreatePayload extends GitPayload {
    public static final String REF_TYPE_REPOSITORY = "repository";
    public static final String REF_TYPE_TAG = "tag";
    public static final String REF_TYPE_BRANCH = "branch";

    private String ref;
    @SerializedName("ref_type")
    private String refType;
    @SerializedName("master_branch")
    private String masterBranch;
    private String description;
    @SerializedName("pusher_type")
    private String pusherType;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getMasterBranch() {
        return masterBranch;
    }

    public void setMasterBranch(String masterBranch) {
        this.masterBranch = masterBranch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPusherType() {
        return pusherType;
    }

    public void setPusherType(String pusherType) {
        this.pusherType = pusherType;
    }
}
