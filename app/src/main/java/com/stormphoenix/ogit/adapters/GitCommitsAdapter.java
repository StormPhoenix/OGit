package com.stormphoenix.ogit.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.stormphoenix.httpknife.github.GitCommit;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.utils.HtmlUtils;
import com.stormphoenix.ogit.utils.ImageUtils;
import com.stormphoenix.ogit.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by StormPhoenix on 17-3-18.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitCommitsAdapter extends BaseRecyclerAdapter<GitCommit> {

    public static final String TAG = GitCommitsAdapter.class.getSimpleName();

    public GitCommitsAdapter(List<GitCommit> dataList) {
        this(null, dataList);
    }

    public GitCommitsAdapter(Context context, List<GitCommit> dataList) {
        super(context, dataList);
    }

    @Override
    public GitCommitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_git_commit, parent, false);
        return new GitCommitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.setIsRecyclable(false);
        GitCommitViewHolder viewHolder = (GitCommitViewHolder) holder;
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

    public static class GitCommitViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.committer_image)
        CircleImageView committerImage;
        @BindView(R.id.text_commit_info)
        TextView textCommitInfo;
        @BindView(R.id.text_commit_happen_time)
        TextView textCommitHappenTime;

        public GitCommitViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(GitCommit model) {
            textCommitInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getCommit().getMessage())));
            textCommitHappenTime.setText(TimeUtils.getRelativeTime(model.getCommit().getAuthor().getDate()));
            setUpHeaderImage(model);
        }

        private void setUpHeaderImage(GitCommit model) {
            DisplayImageOptions options = null;
            options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                    .considerExifParams(true).build();
            committerImage.setTag(model.getAuthor().getAvatarUrl());
            ImageUtils.getInstance().displayImage(model.getAuthor().getAvatarUrl(), committerImage, options);
        }
    }
}
