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
import com.stormphoenix.httpknife.github.payload.GitCreatePayload;
import com.stormphoenix.httpknife.github.payload.GitIssueCommentPayload;
import com.stormphoenix.httpknife.github.payload.GitIssuePayload;
import com.stormphoenix.httpknife.github.payload.GitMemberPayload;
import com.stormphoenix.httpknife.github.payload.GitPullRequestPayload;
import com.stormphoenix.httpknife.github.payload.GitPushPayload;
import com.stormphoenix.httpknife.github.payload.GitReleasePayload;
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
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_git_event, parent, false);
        return new GitEventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.setIsRecyclable(false);
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
        @BindView(R.id.text_commit_nums)
        TextView mTextCommitNums;
        @BindView(R.id.text_commit1_ref)
        TextView mTextCommit1;
        @BindView(R.id.text_commit1_content)
        TextView mTextCommit1Content;
        @BindView(R.id.text_commit2_ref)
        TextView mTextCommit2;
        @BindView(R.id.text_commit2_content)
        TextView mTextCommit2Content;

        public GitEventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(GitEvent model) {
            String eventType = model.getType();
            if (eventType.equals(GitEvent.GIT_WATCH_EVENT)) {
                mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + " stared " + HtmlUtils.bold(model.getRepo().getName())));
                mEventImage.setImageResource(R.drawable.ic_starred_event_black_24dp);
//                BitmapUtils.setIconFont(context, img, OctIcon.STAR, R.color.theme_color);
            } else if (eventType.equals(GitEvent.GIT_FORK_EVENT)) {
                mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + " forked " + HtmlUtils.bold(model.getRepo().getName())));
                mEventImage.setImageResource(R.drawable.ic_forked_event_black_24dp);
//                BitmapUtils.setIconFont(context, img, OctIcon.FORK, R.color.theme_color);
            } else if (eventType.equals(GitEvent.GIT_CREATE_EVENT)) {
                GitCreatePayload payload = (GitCreatePayload) model.getPayload();
                if (payload.getRefType().equals(GitCreatePayload.REF_TYPE_REPOSITORY)) {
                    mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + " created repository " + HtmlUtils.bold(model.getRepo().getName())));
                    mEventImage.setImageResource(R.drawable.ic_created_event_black_24dp);
                } else if (payload.getRefType().equals(GitCreatePayload.REF_TYPE_TAG)) {
                    mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + " created tag " + HtmlUtils.bold(((GitCreatePayload) model.getPayload()).getRef()) + " at " + HtmlUtils.bold(model.getRepo().getName())));
                    mEventImage.setImageResource(R.drawable.ic_tag_black_24dp);
                } else if (payload.getRefType().equals(GitCreatePayload.REF_TYPE_BRANCH)) {
                    mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + " created branch " + HtmlUtils.bold(((GitCreatePayload) model.getPayload()).getRef()) + " at " + HtmlUtils.bold(model.getRepo().getName())));
                    mEventImage.setImageResource(R.drawable.ic_created_event_black_24dp);
                }
//                BitmapUtils.setIconFont(context, img, OctIcon.REPO, R.color.theme_color);
            } else if (eventType.equals(model.GIT_PULL_REQUEST_EVENT)) {
                GitPullRequestPayload payload = (GitPullRequestPayload) model.getPayload();
                mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + " " + payload.getAction() + " pull request " + HtmlUtils.bold(model.getRepo().getName())));
                mTextCommit1Content.setVisibility(View.VISIBLE);
                mTextCommit1Content.setText(payload.getPullRequest().getTitle());
                mEventImage.setImageResource(R.drawable.ic_pull_request_black_24dp);
//                BitmapUtils.setIconFont(context, img, OctIcon.PUSH, R.color.theme_color);
            } else if (eventType.equals(model.GIT_MEMBER_EVENT)) {
                GitMemberPayload payload = (GitMemberPayload) model.getPayload();
                mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + " add " + HtmlUtils.bold(payload.getMember().getLogin()) + " to " + HtmlUtils.bold(model.getRepo().getName())));
                mEventImage.setImageResource(R.drawable.ic_member_black_24dp);
//                BitmapUtils.setIconFont(context, img, OctIcon.PERSON, R.color.theme_color);
            } else if (eventType.equals(model.GIT_ISSUES_EVENT)) {
                GitIssuePayload payload = (GitIssuePayload) model.getPayload();
                mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + " " + payload.getAction() + " issue " + model.getRepo().getName() + "#" + payload.getIssue().getNumber()));
                if (payload.getAction().equals("opened")) {
//                    BitmapUtils.setIconFont(context, img, OctIcon.ISSUE_OPNE, R.color.theme_color);
                } else if (payload.getAction().equals("closed")) {
//                    BitmapUtils.setIconFont(context, img, OctIcon.ISSUE_CLOSE, R.color.theme_color);
                }
            } else if (eventType.equals(GitEvent.GIT_PUBLIC_EVENT)) {
                mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + "<strong> made </strong>" + HtmlUtils.bold(model.getRepo().getName()) + " <strong> public </strong>"));
//                BitmapUtils.setIconFont(context, img, OctIcon.REPO, R.color.theme_color);
            } else if (eventType.equals(GitEvent.GIT_PUSH_EVENT)) {
                GitPushPayload pushPayload = (GitPushPayload) model.getPayload();
                mEventImage.setImageResource(R.drawable.ic_push_event_black_24dp);
                int index = pushPayload.getRef().lastIndexOf('/') + 1;
                String branch = pushPayload.getRef().substring(index);
                mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + " push to " + HtmlUtils.bold(branch) + " at " + HtmlUtils.bold(model.getRepo().getName())));

                mTextCommitNums.setVisibility(View.VISIBLE);
                mTextCommitNums.setText(String.valueOf(pushPayload.getCommits().size()) + " new commits");

                mTextCommit1.setVisibility(View.VISIBLE);
                mTextCommit1.setText(pushPayload.getCommits().get(0).getSha().substring(0, 7) + " ");
                mTextCommit1Content.setVisibility(View.VISIBLE);
                mTextCommit1Content.setText(pushPayload.getCommits().get(0).getMessage());

                if (pushPayload.getCommits().size() > 1) {
                    mTextCommit2.setVisibility(View.VISIBLE);
                    mTextCommit2.setText(pushPayload.getCommits().get(1).getSha().substring(0, 7) + " ");
                    mTextCommit2Content.setVisibility(View.VISIBLE);
                    mTextCommit2Content.setText(pushPayload.getCommits().get(1).getMessage());
                }
            } else if (eventType.equals(GitEvent.GIT_RELEASE_EVENT)) {
                GitReleasePayload payload = (GitReleasePayload) model.getPayload();
                mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + " released to " + HtmlUtils.bold(payload.getRelease().getTagName()) + " at " + HtmlUtils.bold(model.getRepo().getName())));
                mEventImage.setImageResource(R.drawable.ic_tag_black_24dp);
            } else if (eventType.equals(GitEvent.GIT_ISSUE_COMMENT_EVENT)) {
                GitIssueCommentPayload payload = (GitIssueCommentPayload) model.getPayload();
                mTextEventInfo.setText(Html.fromHtml(HtmlUtils.bold(model.getActor().getLogin()) + " commented on pull request " + HtmlUtils.bold(model.getRepo().getName())));
                mEventImage.setImageResource(R.drawable.ic_issue_comment_event_black_24dp);
                mTextCommit1Content.setVisibility(View.VISIBLE);
                mTextCommit1Content.setText(payload.getComment().getBody());
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
            ImageUtils.getInstance().displayImage(model.getActor().getAvatarUrl(), mHeaderImage, options);
        }
    }
}
