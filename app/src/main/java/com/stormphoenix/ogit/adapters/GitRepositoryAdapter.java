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

import com.stormphoenix.httpknife.github.GitRepository;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitRepositoryAdapter extends BaseRecyclerAdapter<GitRepository> {
    public GitRepositoryAdapter(List<GitRepository> dataList) {
        this(null, dataList);
    }

    public GitRepositoryAdapter(Context context, List<GitRepository> dataList) {
        super(context, dataList);
    }

    @Override
    public GitRepositoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_git_repository, parent, false);
        return new GitRepositoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        GitRepositoryHolder viewHolder = (GitRepositoryHolder) holder;
        viewHolder.bind(data.get(position));
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

    public static class GitRepositoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iconType)
        ImageView mIconType;
        @BindView(R.id.repoName)
        TextView mRepoName;
        @BindView(R.id.starSum)
        TextView mStarSum;
        @BindView(R.id.forkSum)
        TextView mForkSum;
        @BindView(R.id.language)
        TextView mLanguage;
        @BindView(R.id.description)
        TextView mDescription;

        public GitRepositoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(GitRepository model) {
            mRepoName.setText(model.getName());
            mStarSum.setText("" + model.getStargazersCount());
            mForkSum.setText("" + model.getForksCount());
            mLanguage.setText(model.getLanguage());
            if (model.isFork()) {
                mIconType.setImageResource(R.drawable.ic_forked_event_black_24dp);
            } else {
                mIconType.setImageResource(R.drawable.ic_created_event_black_24dp);
            }
            mDescription.setText(model.getDescription());
        }
    }
}
