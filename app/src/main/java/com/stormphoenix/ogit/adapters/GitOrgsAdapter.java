package com.stormphoenix.ogit.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stormphoenix.httpknife.github.GitOrganization;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitOrgsAdapter.OrgViewHolder;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by StormPhoenix on 17-3-11.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitOrgsAdapter extends BaseRecyclerAdapter<GitOrganization, OrgViewHolder> {

    public GitOrgsAdapter(List<GitOrganization> dataList) {
        this(null, dataList);
    }

    public GitOrgsAdapter(Context context, List<GitOrganization> dataList) {
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
    public OrgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_git_owner, parent, false);
        return new OrgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrgViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        OrgViewHolder viewHolder = (OrgViewHolder) holder;
        viewHolder.bind(data.get(position));
    }

    public static class OrgViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.header_image)
        CircleImageView mHeaderImage;
        @BindView(R.id.text_user_name)
        TextView mTextUserName;
        @BindView(R.id.text_other_info)
        TextView mTextOtherInfo;

        public OrgViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(GitOrganization org) {
            ImageUtils.getInstance().displayImage(org.getAvatarUrl(), mHeaderImage);
            mTextUserName.setText(org.getLogin());
            if (!TextUtils.isEmpty(org.getDescription())) {
                mTextOtherInfo.setVisibility(View.VISIBLE);
                mTextOtherInfo.setText(org.getDescription());
            }
        }
    }
}
