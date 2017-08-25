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
import com.stormphoenix.httpknife.github.GitTreeItem
import com.stormphoenix.ogit.R
import com.stormphoenix.ogit.adapters.GitFoldersAdapter.GitTreeItemViewHolder
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter
import java.util.*

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 */

class GitFoldersAdapter(context: Context, dataList: MutableList<GitTreeItem>) : BaseRecyclerAdapter<GitTreeItem, GitTreeItemViewHolder>(context, dataList) {

    override fun getAnimators(itemView: View): List<Animator> {
        if (itemView.measuredHeight <= 0) {
            val scaleX = ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f)
            val scaleY = ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f)
            return Arrays.asList<Animator>(scaleX, scaleY)
        }
        return Arrays.asList<Animator>(
                ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f),
                ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GitTreeItemViewHolder {
        val viewItem = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_git_file, parent, false)
        return GitTreeItemViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: GitTreeItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(data[position])
    }

    class GitTreeItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var mImageView: ImageView = itemView.findViewById(R.id.img_file) as ImageView
        internal var mFileText: TextView = itemView.findViewById(R.id.text_file_name) as TextView

        fun bind(model: GitTreeItem) {
            mFileText.text = model.path
            when (model.mode) {
                GitTreeItem.MODE_BLOB, GitTreeItem.MODE_EXECUTABLE -> mImageView.setImageResource(R.drawable.ic_blob_black_24dp)
                GitTreeItem.MODE_SUBDIRECTORY -> mImageView.setImageResource(R.drawable.ic_fold_black_24dp)
            }
        }
    }
}
