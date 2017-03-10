package com.stormphoenix.ogit.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitUserAdapter extends BaseRecyclerAdapter<GitUser> {

    public GitUserAdapter(List<GitUser> dataList) {
        this(null, dataList);
    }

    public GitUserAdapter(Context context, List<GitUser> dataList) {
        super(context, dataList);
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_git_user, parent, false);
        return new GitUserViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        GitUserViewHolder viewHolder = (GitUserViewHolder) holder;
        ((GitUserViewHolder) holder).bind(data.get(position));
    }

    public static class GitUserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.header_image)
        CircleImageView mHeaderImage;
        @BindView(R.id.text_user_name)
        TextView mTextUserName;

        public GitUserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(GitUser model) {
            DisplayImageOptions options = null;
            options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                    .considerExifParams(true).build();
            ImageUtils.getInstance().displayImage(model.getAvatarUrl(), mHeaderImage, options);
            mTextUserName.setText(model.getLogin());
        }
    }
}
