package com.stormphoenix.httpknife.github

import com.google.gson.annotations.SerializedName
import com.stormphoenix.httpknife.github.payload.GitPayload

import java.util.Date

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

class GitEvent {

    var id: String? = null
    var type: String? = null
    var actor: GitUser? = null
    var repo: GitRepository? = null
    @SerializedName("created_at")
    var createdDate: Date? = null
    // payload需要根据type的类型进行转型
    var payload: GitPayload? = null
    var org: GitUser? = null

    companion object {
        val GIT_RELEASE_EVENT = "ReleaseEvent"
        val GIT_WATCH_EVENT = "WatchEvent"
        val GIT_PUSH_EVENT = "PushEvent"
        val GIT_FORK_EVENT = "ForkEvent"
        val GIT_CREATE_EVENT = "CreateEvent"
        val GIT_PULL_REQUEST_EVENT = "PullRequestEvent"
        val GIT_MEMBER_EVENT = "MemberEvent"
        val GIT_ISSUES_EVENT = "IssuesEvent"
        val GIT_PUBLIC_EVENT = "PublicEvent"
        val GIT_ISSUE_COMMENT_EVENT = "IssueCommentEvent"
        val GIT_COMMIT_COMMENT_EVENT = "CommitCommentEvent"
    }
}
