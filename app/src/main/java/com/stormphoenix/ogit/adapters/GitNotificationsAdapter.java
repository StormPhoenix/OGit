package com.stormphoenix.ogit.adapters;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stormphoenix.httpknife.github.GitNotification;
import com.stormphoenix.httpknife.github.GitSubject;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitNotificationsAdapter extends BaseRecyclerAdapter<GitNotification> {

    public GitNotificationsAdapter(List<GitNotification> dataList) {
        this(null, dataList);
    }

    public GitNotificationsAdapter(Context context, List<GitNotification> dataList) {
        super(context, dataList);
    }

    @Override
    protected Animator[] getAnimators(View itemView) {
        return new Animator[0];
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_git_notification, parent, false);
        NotifyViewHolder viewHolder = new NotifyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        NotifyViewHolder viewHolder = (NotifyViewHolder) holder;
        viewHolder.bind(data.get(position));
    }

    public static class NotifyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_notification)
        ImageView imgNotify;
        @BindView(R.id.text_notify_name)
        TextView textNotifyName;
        @BindView(R.id.text_notify_content)
        TextView textNotifyContent;
        @BindView(R.id.text_notify_time)
        TextView textNotifyTime;

        public NotifyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(GitNotification notification) {
            textNotifyName.setText(notification.getRepository().getFullName());
            textNotifyContent.setText(notification.getSubject().getTitle());
            switch (notification.getSubject().getType()) {
                case GitSubject.SUBJECT_TYPE_ISSUE:
                    imgNotify.setImageResource(R.drawable.ic_issue_opened_24dp);
                    textNotifyTime.setText(TimeUtils.getRelativeTime(notification.getUpdatedAt()));
                    break;
                case GitSubject.SUBJECT_TYPE_PULL_REQUEST:
                    imgNotify.setImageResource(R.drawable.ic_pull_request_purple_24dp);
                    break;
                case GitSubject.SUBJECT_TYPE_RELEASE:
                    imgNotify.setImageResource(R.drawable.ic_tag_grey_24dp);
                    break;
                case GitSubject.SUBJECT_TYPE_COMMIT:
                    break;
                default:
                    break;
            }
        }
    }
}
