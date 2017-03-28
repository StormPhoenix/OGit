package com.stormphoenix.ogit.adapters.commits;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.stormphoenix.httpknife.github.GitCommitComment;
import com.stormphoenix.httpknife.github.GitCommitFile;
import com.stormphoenix.httpknife.github.GitFullCommitFile;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.base.MultiTypeAdapter;
import com.stormphoenix.ogit.mvp.ui.fragments.commits.DiffStyler;
import com.stormphoenix.ogit.shares.StyledText;
import com.stormphoenix.ogit.shares.ViewUpdater;
import com.stormphoenix.ogit.utils.TimeUtils;
import com.stormphoenix.ogit.utils.ViewUtils;

import java.util.List;

/**
 * Created by StormPhoenix on 17-3-23.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitCommitDetailsAdapter extends MultiTypeAdapter {
    public static final String TAG = GitCommitDetailsAdapter.class.getSimpleName();
    /**
     * 一个commit信息，必然有提交人、提交总记录信息。
     * TYPE_FILE_HEADER 用于表示以上所说的类型
     */
    private static final int TYPE_FILE_HEADER = 0;

    /**
     * 代表该 itemView 是一行代码
     */
    private static final int TYPE_FILE_LINE_CODE = 1;

    /**
     * 代表该 itemView 是一行评论
     */
    private static final int TYPE_FILE_LINE_COMMENT = 2;

    /**
     * 代表该 itemView 一段评论
     */
    private static final int TYPE_FILE_COMMENT = 3;

    private final DiffStyler diffStyler;

    private final int addedLineColor;

    private final int removedLineColor;

    @Override
    protected int[] getItemTypes() {
        return new int[]{
                TYPE_FILE_HEADER,
                TYPE_FILE_LINE_CODE,
                TYPE_FILE_LINE_COMMENT,
                TYPE_FILE_COMMENT
        };
    }

    public GitCommitDetailsAdapter(final Context context, final LayoutInflater inflater,
                                   final DiffStyler diffStyler) {
        super(context, inflater);
        this.diffStyler = diffStyler;
        Resources resources = inflater.getContext().getResources();
        addedLineColor = resources.getColor(R.color.diff_add_text);
        removedLineColor = resources.getColor(R.color.diff_remove_text);
    }

    @Override
    public long getItemId(int position) {
        switch (getItemViewType(position)) {
            case TYPE_FILE_HEADER:
                String sha = ((GitCommitFile) getItem(position)).getSha();
                if (!TextUtils.isEmpty(sha)) {
                    return sha.hashCode();
                } else {
                    return super.getItemId(position);
                }
            case TYPE_FILE_COMMENT:
            case TYPE_FILE_LINE_COMMENT:
                return ((GitCommitComment) getItem(position)).getId();
            default:
                return super.getItemId(position);
        }
    }

    @Override
    protected void update(int position, Object item, int type) {
        switch (type) {
            case TYPE_FILE_HEADER:
                GitCommitFile file = (GitCommitFile) item;
                String path = file.getFilename();
                int lastSlash = path.lastIndexOf('/');
                if (lastSlash != -1) {
                    setText(0, path.substring(lastSlash + 1));
                    ViewUtils.setGone(setText(1, path.substring(0, lastSlash + 1)),
                            false);
                } else {
                    setText(0, path);
                    setGone(1, true);
                }

                StyledText stats = new StyledText();
                stats.foreground('+', addedLineColor);
                stats.foreground(ViewUpdater.FORMAT_INT.format(file.getAdditions()),
                        addedLineColor);
                stats.append(' ').append(' ').append(' ');
                stats.foreground('-', removedLineColor);
                stats.foreground(ViewUpdater.FORMAT_INT.format(file.getDeletions()),
                        removedLineColor);
                setText(2, stats);
                return;
            case TYPE_FILE_LINE_CODE:
                CharSequence text = (CharSequence) item;
                diffStyler.updateColors((CharSequence) item, setText(0, text));
                return;
            case TYPE_FILE_LINE_COMMENT:
            case TYPE_FILE_COMMENT:
                GitCommitComment comment = (GitCommitComment) item;
//                    avatars.bind(imageView(1), comment.getUser());
                setText(2, comment.getUser().getLogin());
                setText(3, TimeUtils.getRelativeTime(comment.getUpdatedAt()));
//                imageGetter.bind(textView(0), comment.getBodyHtml(), comment.getId());
                return;
        }
    }

    public void addFullCommitFile(GitFullCommitFile file) {
        addItem(TYPE_FILE_HEADER, file.getCommitFile());
        List<CharSequence> lines = diffStyler.get(file.getCommitFile().getFilename());
        int num = 0;
        for (CharSequence line : lines) {
            addItem(TYPE_FILE_LINE_CODE, line);
            for (GitCommitComment comment : file.get(num)) {
                addItem(TYPE_FILE_LINE_COMMENT, comment);
                num++;
            }
        }
    }

    public void addCommitFile(GitCommitFile file) {
        addItem(TYPE_FILE_HEADER, file);
        addItems(TYPE_FILE_LINE_CODE, diffStyler.get(file.getFilename()));
    }

    public void addComment(final GitCommitComment comment) {
        addItem(TYPE_FILE_COMMENT, comment);
    }

    @Override
    protected int[] getChildrentIds(int type) {
        switch (type) {
            case TYPE_FILE_HEADER:
                return new int[]{R.id.tv_name, R.id.tv_folder, R.id.tv_stats};
            case TYPE_FILE_LINE_CODE:
                return new int[]{R.id.tv_diff};
            case TYPE_FILE_LINE_COMMENT:
            case TYPE_FILE_COMMENT:
                return new int[]{
                        R.id.tv_comment_body,
                        R.id.iv_avatar,
                        R.id.tv_comment_author,
                        R.id.tv_comment_date
                };
            default:
                return null;
        }
    }

    @Override
    protected int getLayoutId(final int type) {
        switch (type) {
            case TYPE_FILE_HEADER:
                return R.layout.commit_diff_file_header;
            case TYPE_FILE_LINE_CODE:
                return R.layout.commit_diff_line;
            case TYPE_FILE_LINE_COMMENT:
                return R.layout.diff_comment_item;
            case TYPE_FILE_COMMENT:
                return R.layout.commit_comment_item;
            default:
                return -1;
        }
    }
}
