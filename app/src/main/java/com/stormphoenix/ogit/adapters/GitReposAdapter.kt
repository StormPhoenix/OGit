package com.stormphoenix.ogit.adapters

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stormphoenix.httpknife.github.GitRepository
import com.stormphoenix.ogit.R
import com.stormphoenix.ogit.adapters.GitReposAdapter.GitRepositoryHolder
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter
import java.util.*

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

class GitReposAdapter(context: Context, dataList: MutableList<GitRepository>) : BaseRecyclerAdapter<GitRepository, GitRepositoryHolder>(context, dataList) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GitRepositoryHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_git_repository, parent, false)
        return GitRepositoryHolder(itemView)
    }

    override fun onBindViewHolder(holder: GitRepositoryHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(data[position])
    }

    override fun getAnimators(itemView: View): List<Animator> {
        if (itemView.measuredHeight <= 0) {
            val scaleX = ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f)
            val scaleY = ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f)
            return Arrays.asList<Animator>(scaleX, scaleY)
        }
        return Arrays.asList<Animator>(
                ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f),
                ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f)
        )
    }

    class GitRepositoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var mIconType: AppCompatImageView? = null
        internal var mRepoName: TextView? = null
        internal var mStarSum: TextView? = null
        internal var mForkSum: TextView? = null
        internal var mLanguage: TextView? = null
        internal var mDescription: TextView? = null

        init {
            mIconType = itemView.findViewById(R.id.iconType) as AppCompatImageView?
            mRepoName = itemView.findViewById(R.id.repoName) as TextView?
            mStarSum = itemView.findViewById(R.id.starSum) as TextView?
            mForkSum = itemView.findViewById(R.id.forkSum) as TextView?
            mLanguage = itemView.findViewById(R.id.language) as TextView?
            mDescription = itemView.findViewById(R.id.description) as TextView?
        }

        fun bind(model: GitRepository) {
            mRepoName!!.text = model.name
            mStarSum!!.text = "" + model.stargazersCount
            mForkSum!!.text = "" + model.forksCount
            mLanguage!!.text = model.language
            if (model.isFork) {
                mIconType!!.setImageResource(R.drawable.ic_forked_event_black_24dp)
            } else {
                mIconType!!.setImageResource(R.drawable.ic_created_event_black_24dp)
            }
            if (model.description != null && !TextUtils.isEmpty(model.description)) {
                mDescription!!.visibility = View.VISIBLE
                mDescription!!.text = model.description
            } else {
                mDescription!!.visibility = View.GONE
            }
        }
    }
}
