package com.stormphoenix.ogit.adapters.commits

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.stormphoenix.httpknife.github.GitCommit
import com.stormphoenix.httpknife.github.GitCommitMessage
import com.stormphoenix.httpknife.github.GitUser
import com.stormphoenix.ogit.OGitApplication
import com.stormphoenix.ogit.R
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter
import com.stormphoenix.ogit.adapters.commits.GitCommitsAdapter.GitCommitViewHolder
import com.stormphoenix.ogit.utils.HtmlUtils
import com.stormphoenix.ogit.utils.ImageUtils
import com.stormphoenix.ogit.utils.TimeUtils
import com.stormphoenix.ogit.utils.UserUtils

import java.util.Date

import butterknife.BindView
import butterknife.ButterKnife
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

class GitCommitsAdapter(context: Context, dataList: MutableList<GitCommit>) : BaseRecyclerAdapter<GitCommit, GitCommitViewHolder>(context, dataList) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GitCommitViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_git_commit, parent, false)
        return GitCommitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GitCommitViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.setIsRecyclable(false)
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class GitCommitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.committer_image)
        internal var committerImage: CircleImageView? = null
        @BindView(R.id.text_commit_info)
        internal var textCommitInfo: TextView? = null
        @BindView(R.id.text_commit_happen_time)
        internal var textCommitHappenTime: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(model: GitCommit) {
            textCommitInfo!!.text = Html.fromHtml(HtmlUtils.bold(model.commit.message))
            textCommitHappenTime!!.text = TimeUtils.getRelativeTime(getAuthorDate(model))
            bindAuthorImage(model, committerImage)
        }

        private fun getAuthorDate(commit: GitCommit): Date? {
            val commitMessage = commit.commit ?: return null
            val author = commitMessage.author
            return author?.date
        }

        private fun getAuthorName(model: GitCommit): String? {
            val author = model.author
            if (author != null) {
                return author.login
            }

            val commit = model.commit ?: return null

            val commitAuthor = commit.author
            return commitAuthor?.name
        }

        private fun bindAuthorImage(model: GitCommit, imageView: ImageView?) {
            var options: DisplayImageOptions? = null
            options = DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                    .considerExifParams(true).build()
            val author = model.author
            if (author != null) {
                Log.e(TAG, "AuthorImage: " + model.author.avatarUrl)
                ImageUtils.getInstance().displayImage(model.author.avatarUrl, imageView, options)
            } else {
                imageView!!.setColorFilter(OGitApplication.instance.resources.getColor(R.color.colorDefaultHeader))
            }
        }

        private fun getAvatarUrl(author: GitUser?): String? {
            return if (author == null) {
                null
            } else UserUtils.getAvatarUrl(UserUtils.getHash(author.email))
        }


        private fun setUpHeaderImage(model: GitCommit) {
            committerImage!!.tag = model.author.avatarUrl
        }
    }

    companion object {

        val TAG = GitCommitsAdapter::class.java.simpleName
    }
}
