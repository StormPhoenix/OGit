package com.stormphoenix.ogit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.stormphoenix.ogit.adapters.base.MultiTypeAdapter;

/**
 * Created by StormPhoenix on 17-3-23.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitCommitDetailsAdapter extends MultiTypeAdapter {
    private static final int TYPE_HEADER = 0;

    private static final int TYPE_LINE_CONTENT = 1;

    private static final int TYPE_LINE_CONTENT_COMMENT = 2;

    private static final int TYPE_COMMENT = 3;

    private static int[] types = new int[]{
            TYPE_HEADER,
            TYPE_LINE_CONTENT,
            TYPE_LINE_CONTENT_COMMENT,
            TYPE_COMMENT
    };

    public GitCommitDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected int getTypeCount() {
        return types.length;
    }

    @Override
    protected int getLayoutId(int type) {
        return 0;
    }

    @Override
    protected int[] getTypes() {
        return types;
    }
}
