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

import com.stormphoenix.httpknife.github.GitTrendRepository;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitReposAdapter2 extends BaseRecyclerAdapter<GitTrendRepository, GitReposAdapter2.GitRepositoryHolder2> {
    public GitReposAdapter2(List<GitTrendRepository> dataList) {
        this(null, dataList);
    }

    public GitReposAdapter2(Context context, List<GitTrendRepository> dataList) {
        super(context, dataList);
    }

    @Override
    public GitRepositoryHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_git_repository2, parent, false);
        return new GitRepositoryHolder2(itemView);
    }

    @Override
    public void onBindViewHolder(GitRepositoryHolder2 holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(data.get(position));
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

    public static class GitRepositoryHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_trend_repo_title)
        TextView mTrendRepoTitle;
        @BindView(R.id.tv_description)
        TextView mRepoDesc;
        @BindView(R.id.avatar1)
        ImageView mAvatar1;
        @BindView(R.id.avatar2)
        ImageView mAvatar2;
        @BindView(R.id.avatar3)
        ImageView mAvatar3;
        @BindView(R.id.avatar4)
        ImageView mAvatar4;
        @BindView(R.id.avatar5)
        ImageView mAvatar5;
        @BindView(R.id.star)
        ImageView mStar;
        @BindView(R.id.tv_lang_type)
        TextView mLangType;
        @BindView(R.id.tv_star_per_duration)
        TextView mStarPerDuration;
        @BindView(R.id.tv_built_by)
        TextView mBuildByText;

        public GitRepositoryHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(GitTrendRepository model) {
            mTrendRepoTitle.setText(model.getOwnerName() + " / " + model.getRepoName());
            mRepoDesc.setText(model.getRepoDesc());
            if (model.getStarPerDuration() == null) {
                mStarPerDuration.setVisibility(View.INVISIBLE);
            } else {
                mStarPerDuration.setText(model.getStarPerDuration());
            }
            mLangType.setText(model.getLangType());

            List<ImageView> imageViewList = new ArrayList<>();
            imageViewList.add(mAvatar1);
            imageViewList.add(mAvatar2);
            imageViewList.add(mAvatar3);
            imageViewList.add(mAvatar4);
            imageViewList.add(mAvatar5);
            for (ImageView imageView : imageViewList) {
                imageView.setVisibility(View.GONE);
            }

            if (model.getContibutorUrl().size() == 0) {
                mBuildByText.setVisibility(View.GONE);
            } else {
                mBuildByText.setVisibility(View.VISIBLE);
                for (int i = 0; i < model.getContibutorUrl().size(); i++) {
                    ImageUtils.getInstance().displayImage(model.getContibutorUrl().get(i), imageViewList.get(i));
                    imageViewList.get(i).setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
