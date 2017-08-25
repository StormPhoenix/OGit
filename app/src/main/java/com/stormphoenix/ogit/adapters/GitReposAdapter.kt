package com.stormphoenix.ogit.adapters

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.stormphoenix.httpknife.github.GitRepository
import com.stormphoenix.ogit.R
import com.stormphoenix.ogit.adapters.GitReposAdapter.GitRepositoryHolder
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter

import java.util.Arrays

import butterknife.BindView
import butterknife.ButterKnife
import com.stormphoenix.ogit.R.id.*

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
        internal var mIconType: ImageView? = null
        internal var mRepoName: TextView? = null
        internal var mStarSum: TextView? = null
        internal var mForkSum: TextView? = null
        internal var mLanguage: TextView? = null
        internal var mDescription: TextView? = null

        init {
              mIconType = itemView.findViewById(iconType) as ImageView?
              mRepoName = itemView.findViewById(repoName) as TextView?
              mStarSum = itemView.findViewById(starSum) as TextView?
              mForkSum = itemView.findViewById(forkSum) as TextView?
              mLanguage= itemView.findViewById(language) as TextView?
              mDescription = itemView.findViewById(description) as TextView?
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
            mDescription!!.text = model.description
        }
    }
}
