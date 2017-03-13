package com.stormphoenix.httpknife.github;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by StormPhoenix on 17-3-12.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitRepoSearch {
    @SerializedName("total_count")
    private int totalCount;
    @SerializedName("incomplete_results")
    private boolean incompleteResults;
    private List<GitRepository> items;

    public List<GitRepository> getItems() {
        return items;
    }

    public void setItems(List<GitRepository> items) {
        this.items = items;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
