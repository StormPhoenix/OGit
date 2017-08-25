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
import butterknife.BindView
import butterknife.ButterKnife
import com.stormphoenix.httpknife.github.GitTrendRepository
import com.stormphoenix.ogit.R
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter
import com.stormphoenix.ogit.utils.ImageUtils
import java.util.*

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

class GitReposAdapter2(context: Context, dataList: MutableList<GitTrendRepository>) : BaseRecyclerAdapter<GitTrendRepository, GitReposAdapter2.GitRepositoryHolder2>(context, dataList) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GitRepositoryHolder2 {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_git_repository2, parent, false)
        return GitRepositoryHolder2(itemView)
    }

    override fun onBindViewHolder(holder: GitRepositoryHolder2, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(data[position])
    }

    override fun getAnimators(itemView: View): List<Animator> {
        if (itemView.measuredHeight <= 0) {
            val scaleX = ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f)
            val scaleY = ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f)
            return Arrays.asList<Animator>(
                    scaleX, scaleY

            )
        }
        return Arrays.asList<Animator>(
                ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f),
                ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f)
        )
    }

    class GitRepositoryHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.tv_trend_repo_title)
        internal var mTrendRepoTitle: TextView? = null
        @BindView(R.id.tv_description)
        internal var mRepoDesc: TextView? = null
        @BindView(R.id.avatar1)
        internal var mAvatar1: ImageView? = null
        @BindView(R.id.avatar2)
        internal var mAvatar2: ImageView? = null
        @BindView(R.id.avatar3)
        internal var mAvatar3: ImageView? = null
        @BindView(R.id.avatar4)
        internal var mAvatar4: ImageView? = null
        @BindView(R.id.avatar5)
        internal var mAvatar5: ImageView? = null
        @BindView(R.id.star)
        internal var mStar: ImageView? = null
        @BindView(R.id.tv_lang_type)
        internal var mLangType: TextView? = null
        @BindView(R.id.tv_star_per_duration)
        internal var mStarPerDuration: TextView? = null
        @BindView(R.id.tv_built_by)
        internal var mBuildByText: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(model: GitTrendRepository) {
            mTrendRepoTitle!!.text = model.ownerName + " / " + model.repoName
            mRepoDesc!!.text = model.repoDesc
            if (model.starPerDuration == null) {
                mStarPerDuration!!.visibility = View.INVISIBLE
            } else {
                mStarPerDuration!!.text = model.starPerDuration
            }
            mLangType!!.text = model.langType

            val imageViewList = ArrayList<ImageView>()
            imageViewList.add(this.mAvatar1!!)
            imageViewList.add(this.mAvatar2!!)
            imageViewList.add(this.mAvatar3!!)
            imageViewList.add(this.mAvatar4!!)
            imageViewList.add(this.mAvatar5!!)
            for (imageView in imageViewList) {
                imageView.visibility = View.GONE
            }

            if (model.contibutorUrl.size == 0) {
                mBuildByText!!.visibility = View.GONE
            } else {
                mBuildByText!!.visibility = View.VISIBLE
                for (i in 0..model.contibutorUrl.size - 1) {
                    ImageUtils.getInstance().displayImage(model.contibutorUrl[i], imageViewList[i])
                    imageViewList[i].visibility = View.VISIBLE
                }
            }
        }
    }
}
