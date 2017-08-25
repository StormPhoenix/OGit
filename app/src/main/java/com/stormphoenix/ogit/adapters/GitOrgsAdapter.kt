package com.stormphoenix.ogit.adapters

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.stormphoenix.httpknife.github.GitOrganization
import com.stormphoenix.ogit.R
import com.stormphoenix.ogit.adapters.GitOrgsAdapter.OrgViewHolder
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter
import com.stormphoenix.ogit.utils.ImageUtils
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

class GitOrgsAdapter(context: Context, dataList: MutableList<GitOrganization>) : BaseRecyclerAdapter<GitOrganization, OrgViewHolder>(context, dataList) {

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

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OrgViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_git_owner, parent, false)
        return OrgViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrgViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(data[position])
    }

    class OrgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var mHeaderImage: CircleImageView? = null
        internal var mTextUserName: TextView? = null
        internal var mTextOtherInfo: TextView? = null

        init {
            mHeaderImage = itemView.findViewById(R.id.header_image) as CircleImageView?
            mTextUserName = itemView.findViewById(R.id.text_user_name) as TextView?
            mTextOtherInfo = itemView.findViewById(R.id.text_other_info) as TextView?
        }

        fun bind(org: GitOrganization) {
            ImageUtils.getInstance().displayImage(org.avatarUrl, mHeaderImage)
            mTextUserName!!.text = org.login
            if (!TextUtils.isEmpty(org.description)) {
                mTextOtherInfo!!.visibility = View.VISIBLE
                mTextOtherInfo!!.text = org.description
            }
        }
    }
}
