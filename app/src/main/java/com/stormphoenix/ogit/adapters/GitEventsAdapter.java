package com.stormphoenix.ogit.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.httpknife.github.GitEvent;
import com.stormphoenix.ogit.shares.OGitImageLoader;

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
            ButterKnife.bind(itemView);
        }

        public void bind(GitEvent model) {
            DisplayImageOptions options = null;
            options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                    .considerExifParams(true).build();
            mHeaderImage.setTag(model.getActor().getAvatarUrl());
            OGitImageLoader.getInstance().displayImage(model.getActor().getAvatarUrl(), mHeaderImage, options);
        }
    }
}
