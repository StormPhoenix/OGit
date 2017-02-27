package com.stormphoenix.ogit.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.httpknife.github.payload.GitIssuePayload;
import com.stormphoenix.httpknife.github.payload.GitMemberPayload;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.shares.OGitImageLoader;
import com.stormphoenix.ogit.shares.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitEventsAdapter extends BaseRecyclerAdapter<GitEvent> {

    public GitEventsAdapter(List<GitEvent> dataList) {
        this(null, dataList);
    }

    public GitEventsAdapter(Context context, List<GitEvent> dataList) {
        super(context, dataList);
    }

    @Override
    public GitEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_git_event_item, parent, false);
        return new GitEventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GitEventViewHolder viewHolder = (GitEventViewHolder) holder;
        viewHolder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected Animator[] getAnimators(View itemView) {
        if (itemView.getMeasuredHeight() <= 0) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f);
            return new ObjectAnimator[]{scaleX, scaleY};
        }
        return new Animator[]{
                ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f),
                ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f),
        };
    }

    public static class GitEventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.header_image)
        CircleImageView mHeaderImage;
        @BindView(R.id.event_image)
        ImageView mEventImage;
        @BindView(R.id.text_event_info)
        TextView mTextEventInfo;
        @BindView(R.id.text_event_happen_time)
        TextView mTextEventHappenTime;

        public GitEventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(GitEvent model) {
            String eventType = model.getType();
            if (eventType.equals(GitEvent.GIT_WATCH_EVENT)) {
                mTextEventInfo.setText(Html.fromHtml(model.getActor().getLogin() + " <b>starred</b> " + model.getRepo().getName()));
                mEventImage.setImageResource(R.drawable.ic_starred_event_black_24dp);
//                BitmapUtils.setIconFont(context, img, OctIcon.STAR, R.color.theme_color);
            } else if (eventType.equals(GitEvent.GIT_FORK_EVENT)) {
                mTextEventInfo.setText(Html.fromHtml(model.getActor().getLogin() + " <b>forked</b> " + model.getRepo().getName()));
                mEventImage.setImageResource(R.drawable.ic_forked_event_black_24dp);
//                BitmapUtils.setIconFont(context, img, OctIcon.FORK, R.color.theme_color);
            } else if (eventType.equals(GitEvent.GIT_CREATE_EVENT)) {
                mTextEventInfo.setText(Html.fromHtml(model.getActor().getLogin() + " <b>created repo</b> " + model.getRepo().getName()));
                mEventImage.setImageResource(R.drawable.ic_created_event_black_24dp);
//                BitmapUtils.setIconFont(context, img, OctIcon.REPO, R.color.theme_color);
            } else if (eventType.equals(model.GIT_PULL_REQUEST_EVENT)) {
                mTextEventInfo.setText(Html.fromHtml(model.getActor().getLogin() + " <b>opened pull request</b> " + model.getRepo().getName()));
//                BitmapUtils.setIconFont(context, img, OctIcon.PUSH, R.color.theme_color);
            } else if (eventType.equals(model.GIT_MEMBER_EVENT)) {
                GitMemberPayload payload = (GitMemberPayload) model.getPayload();
                mTextEventInfo.setText(Html.fromHtml(model.getActor().getLogin() + " <b> add " + payload.getMember().getLogin() + " to </b> " + model.getRepo().getName()));
//                BitmapUtils.setIconFont(context, img, OctIcon.PERSON, R.color.theme_color);
            } else if (eventType.equals(model.GIT_ISSUES_EVENT)) {
                GitIssuePayload payload = (GitIssuePayload) model.getPayload();
                mTextEventInfo.setText(Html.fromHtml(model.getActor().getLogin() + " <b> " + payload.getAction() + " issue </b> " + model.getRepo().getName() + "#" + payload.getIssue().getNumber()));
                if (payload.getAction().equals("opened")) {
//                    BitmapUtils.setIconFont(context, img, OctIcon.ISSUE_OPNE, R.color.theme_color);
                } else if (payload.getAction().equals("closed")) {
//                    BitmapUtils.setIconFont(context, img, OctIcon.ISSUE_CLOSE, R.color.theme_color);
                }
            } else if (eventType.equals(GitEvent.GIT_PUBLIC_EVENT)) {
                mTextEventInfo.setText(Html.fromHtml(model.getActor().getLogin() + "<strong> made </strong>" + model.getRepo().getName() + " <strong> public </strong>"));
//                BitmapUtils.setIconFont(context, img, OctIcon.REPO, R.color.theme_color);
            } else {
                mTextEventInfo.setText("Unknown event type");   // I will add more eventtype later
            }

            mTextEventHappenTime.setText(TimeUtils.getRelativeTime(model.getCreatedDate()));
            setUpHeaderImage(model);
        }

        private void setUpHeaderImage(GitEvent model) {
            DisplayImageOptions options = null;
            options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                    .considerExifParams(true).build();
            mHeaderImage.setTag(model.getActor().getAvatarUrl());
            OGitImageLoader.getInstance().displayImage(model.getActor().getAvatarUrl(), mHeaderImage, options);
        }
    }
}
