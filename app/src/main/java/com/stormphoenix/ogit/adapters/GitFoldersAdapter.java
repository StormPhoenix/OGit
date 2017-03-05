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

import com.stormphoenix.httpknife.github.GitTreeItem;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitFoldersAdapter extends BaseRecyclerAdapter<GitTreeItem> {
    public GitFoldersAdapter(List<GitTreeItem> dataList) {
        this(null, dataList);
    }

    public GitFoldersAdapter(Context context, List<GitTreeItem> dataList) {
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
        View viewItem = LayoutInflater.from(mContext).inflate(R.layout.list_git_file_item, parent, false);
        return new GitTreeItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ((GitTreeItemViewHolder) holder).bind(data.get(position));
    }

    public static class GitTreeItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_file)
        ImageView mImageView;
        @BindView(R.id.text_file_name)
        TextView mFileText;

        public GitTreeItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(GitTreeItem model) {
            mFileText.setText(model.getPath());

            switch (model.getMode()) {
                case GitTreeItem.MODE_BLOB:
                case GitTreeItem.MODE_EXECUTABLE:
                    mImageView.setImageResource(R.drawable.ic_blob_black_24dp);
                    break;
                case GitTreeItem.MODE_SUBDIRECTORY:
                    mImageView.setImageResource(R.drawable.ic_fold_black_24dp);
                    break;
            }
        }
    }
}
