package com.stormphoenix.ogit.adapters.base

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.stormphoenix.httpknife.github.GitEvent
import com.stormphoenix.httpknife.github.payload.*
import com.stormphoenix.ogit.R
import com.stormphoenix.ogit.adapters.base.GitEventsAdapter.GitEventViewHolder
import com.stormphoenix.ogit.utils.HtmlUtils
import com.stormphoenix.ogit.utils.ImageUtils
import com.stormphoenix.ogit.utils.TimeUtils
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

class GitEventsAdapter(context: Context, dataList: MutableList<GitEvent>) : BaseRecyclerAdapter<GitEvent, GitEventViewHolder>(context, dataList) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GitEventViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_git_event, parent, false)
        return GitEventViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GitEventViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.setIsRecyclable(false)
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getAnimators(itemView: View): List<Animator> {
        if (itemView.measuredHeight <= 0) {
            val scaleX = ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f)
            val scaleY = ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f)
            return Arrays.asList<Animator>(scaleX, scaleY);
        }
        return Arrays.asList<Animator>(
                ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f),
                ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f));
    }

    class GitEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var mHeaderImage: CircleImageView? = null
        internal var mEventImage: ImageView? = null
        internal var mTextEventInfo: TextView? = null
        internal var mTextEventHappenTime: TextView? = null
        internal var mTextCommitNums: TextView? = null
        internal var mTextCommit1: TextView? = null
        internal var mTextCommit1Content: TextView? = null
        internal var mTextCommit2: TextView? = null
        internal var mTextCommit2Content: TextView? = null
        internal var mTextUserName: TextView? = null

        init {
            mHeaderImage = itemView.findViewById(R.id.header_image) as CircleImageView?
            mEventImage = itemView.findViewById(R.id.event_image) as ImageView?
            mTextEventInfo = itemView.findViewById(R.id.text_event_info) as TextView?
            mTextEventHappenTime = itemView.findViewById(R.id.text_event_happen_time) as TextView?
            mTextCommitNums = itemView.findViewById(R.id.text_commit_nums) as TextView?
            mTextCommit1 = itemView.findViewById(R.id.text_commit1_ref) as TextView?
            mTextCommit1Content = itemView.findViewById(R.id.text_commit1_content) as TextView?
            mTextCommit2 = itemView.findViewById(R.id.text_commit2_ref) as TextView?
            mTextCommit2Content = itemView.findViewById(R.id.text_commit2_content) as TextView?
            mTextUserName = itemView.findViewById(R.id.text_user_name) as TextView?
        }

        fun bind(model: GitEvent) {
            val eventType = model.type
            mTextUserName?.text = model.actor?.name
            if (eventType == GitEvent.GIT_WATCH_EVENT) {
                mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " stared " + HtmlUtils.bold(model.repo?.name))
                mEventImage?.setImageResource(R.drawable.ic_starred_event_black_24dp)
                //                BitmapUtils.setIconFont(context, img, OctIcon.STAR, R.color.theme_color);
            } else if (eventType == GitEvent.GIT_COMMIT_COMMENT_EVENT) {
                val payload = model.payload as GitCommitCommentPayload
                mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " comment on " + HtmlUtils.bold(model.repo?.name))
                mTextCommit1Content?.visibility = View.VISIBLE
                mTextCommit1Content?.text = "Comment in " + payload.comment.commitId.substring(0, 11) + ":"
                mTextCommit2Content?.visibility = View.VISIBLE
                mTextCommit2Content?.text = payload.comment.body
                mEventImage?.setImageResource(R.drawable.ic_issue_comment_event_black_24dp)
            } else if (eventType == GitEvent.GIT_FORK_EVENT) {
                mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " forked " + HtmlUtils.bold(model.repo?.name))
                mEventImage?.setImageResource(R.drawable.ic_forked_event_black_24dp)
                //                BitmapUtils.setIconFont(context, img, OctIcon.FORK, R.color.theme_color);
            } else if (eventType == GitEvent.GIT_CREATE_EVENT) {
                val payload = model.payload as GitCreatePayload
                if (payload.refType == GitCreatePayload.REF_TYPE_REPOSITORY) {
                    mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " created repository " + HtmlUtils.bold(model.repo?.name))
                    mEventImage?.setImageResource(R.drawable.ic_created_event_black_24dp)
                } else if (payload.refType == GitCreatePayload.REF_TYPE_TAG) {
                    mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " created tag " + HtmlUtils.bold((model.payload as GitCreatePayload).ref) + " at " + HtmlUtils.bold(model.repo?.name))
                    mEventImage?.setImageResource(R.drawable.ic_tag_black_24dp)
                } else if (payload.refType == GitCreatePayload.REF_TYPE_BRANCH) {
                    mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " created branch " + HtmlUtils.bold((model.payload as GitCreatePayload).ref) + " at " + HtmlUtils.bold(model.repo?.name))
                    mEventImage?.setImageResource(R.drawable.ic_created_event_black_24dp)
                }
                //                BitmapUtils.setIconFont(context, img, OctIcon.REPO, R.color.theme_color);
            } else if (eventType == GitEvent.GIT_PULL_REQUEST_EVENT) {
                val payload = model.payload as GitPullRequestPayload
                mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " " + payload.action + " pull request " + HtmlUtils.bold(model.repo?.name))
                mTextCommit1Content?.visibility = View.VISIBLE
                mTextCommit1Content?.text = payload.pullRequest.title
                mEventImage?.setImageResource(R.drawable.ic_pull_request_black_24dp)
                //                BitmapUtils.setIconFont(context, img, OctIcon.PUSH, R.color.theme_color);
            } else if (eventType == GitEvent.GIT_MEMBER_EVENT) {
                val payload = model.payload as GitMemberPayload
                mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " add " + HtmlUtils.bold(payload.member.login) + " to " + HtmlUtils.bold(model.repo?.name))
                mEventImage?.setImageResource(R.drawable.ic_member_black_24dp)
                //                BitmapUtils.setIconFont(context, img, OctIcon.PERSON, R.color.theme_color);
            } else if (eventType == GitEvent.GIT_ISSUES_EVENT) {
                val payload = model.payload as GitIssuePayload
                mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " " + payload.action + " issue " + model.repo?.name + "#" + payload.issue.number)
                if (payload.action == "opened") {
                    mEventImage?.setImageResource(R.drawable.ic_issue_opened_24dp)
                } else if (payload.action == "closed") {
                    mEventImage?.setImageResource(R.drawable.ic_issue_closed_24dp)
                }
            } else if (eventType == GitEvent.GIT_PUBLIC_EVENT) {
                mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + "<strong> made </strong>" + HtmlUtils.bold(model.repo?.name) + " <strong> public </strong>")
                //                BitmapUtils.setIconFont(context, img, OctIcon.REPO, R.color.theme_color);
            } else if (eventType == GitEvent.GIT_PUSH_EVENT) {
                val pushPayload = model.payload as GitPushPayload
                mEventImage?.setImageResource(R.drawable.ic_push_event_black_24dp)
                val index = pushPayload.ref.lastIndexOf('/') + 1
                val branch = pushPayload.ref.substring(index)
                mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " push to " + HtmlUtils.bold(branch) + " at " + HtmlUtils.bold(model.repo?.name))

                mTextCommitNums?.visibility = View.VISIBLE
                mTextCommitNums?.text = pushPayload.commits.size.toString() + " new commits"

                if (pushPayload.commits.size > 0) {
                    mTextCommit1?.visibility = View.VISIBLE
                    mTextCommit1?.text = pushPayload.commits[0].sha.substring(0, 7) + " "
                    mTextCommit1Content?.visibility = View.VISIBLE
                    mTextCommit1Content?.text = pushPayload.commits[0].message
                }

                if (pushPayload.commits.size > 1) {
                    mTextCommit2?.visibility = View.VISIBLE
                    mTextCommit2?.text = pushPayload.commits[1].sha.substring(0, 7) + " "
                    mTextCommit2Content?.visibility = View.VISIBLE
                    mTextCommit2Content?.text = pushPayload.commits[1].message
                }
            } else if (eventType == GitEvent.GIT_RELEASE_EVENT) {
                val payload = model.payload as GitReleasePayload
                mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " released to " + HtmlUtils.bold(payload.release.tagName) + " at " + HtmlUtils.bold(model.repo?.name))
                mEventImage?.setImageResource(R.drawable.ic_tag_black_24dp)
            } else if (eventType == GitEvent.GIT_ISSUE_COMMENT_EVENT) {
                val payload = model.payload as GitIssueCommentPayload
                mTextEventInfo?.text = Html.fromHtml(HtmlUtils.bold(model.actor?.login) + " commented on pull request " + HtmlUtils.bold(model.repo?.name))
                mEventImage?.setImageResource(R.drawable.ic_issue_comment_event_black_24dp)
                mTextCommit1Content?.visibility = View.VISIBLE
                mTextCommit1Content?.text = payload.comment.body
            } else {
                mTextEventInfo?.text = "Unknown event type"   // I will add more eventtype later
            }

            mTextEventHappenTime?.text = TimeUtils.getRelativeTime(model.createdDate)
            setUpHeaderImage(model)
        }

        private fun setUpHeaderImage(model: GitEvent) {
            var options: DisplayImageOptions? = null
            options = DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                    .considerExifParams(true).build()
            mHeaderImage?.tag = model.actor?.avatarUrl
            ImageUtils.getInstance().displayImage(model.actor?.avatarUrl, mHeaderImage, options)
        }
    }
}
