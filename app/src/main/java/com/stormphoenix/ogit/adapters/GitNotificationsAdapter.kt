package com.stormphoenix.ogit.adapters

import android.animation.Animator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.stormphoenix.httpknife.github.GitNotification
import com.stormphoenix.httpknife.github.GitSubject
import com.stormphoenix.ogit.R
import com.stormphoenix.ogit.adapters.GitNotificationsAdapter.NotifyViewHolder
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter
import com.stormphoenix.ogit.utils.TimeUtils

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

class GitNotificationsAdapter(context: Context, dataList: MutableList<GitNotification>) : BaseRecyclerAdapter<GitNotification, NotifyViewHolder>(context, dataList) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NotifyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_git_notification, parent, false)
        return NotifyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotifyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(data[position])
    }

    class NotifyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.img_notification)
        internal var imgNotify: ImageView? = null
        @BindView(R.id.text_notify_name)
        internal var textNotifyName: TextView? = null
        @BindView(R.id.text_notify_content)
        internal var textNotifyContent: TextView? = null
        @BindView(R.id.text_notify_time)
        internal var textNotifyTime: TextView? = null

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(notification: GitNotification) {
            textNotifyName!!.text = notification.repository.fullName
            textNotifyContent!!.text = notification.subject.title
            when (notification.subject.type) {
                GitSubject.SUBJECT_TYPE_ISSUE -> {
                    imgNotify!!.setImageResource(R.drawable.ic_issue_opened_24dp)
                    textNotifyTime!!.text = TimeUtils.getRelativeTime(notification.updatedAt)
                }
                GitSubject.SUBJECT_TYPE_PULL_REQUEST -> imgNotify!!.setImageResource(R.drawable.ic_pull_request_purple_24dp)
                GitSubject.SUBJECT_TYPE_RELEASE -> imgNotify!!.setImageResource(R.drawable.ic_tag_grey_24dp)
                GitSubject.SUBJECT_TYPE_COMMIT -> {
                }
                else -> {
                }
            }
        }
    }
}
