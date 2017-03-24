package com.stormphoenix.ogit.adapters.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

/**
 * Created by StormPhoenix on 17-3-23.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class MultiTypeAdapter extends RecyclerView.Adapter {
    private final LayoutInflater inflater;
    private final int[] layout;
    private final int[] types;
    private final int typeCount;

    public MultiTypeAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.typeCount = getTypeCount();
        this.types = new int[typeCount];
        this.layout = new int[typeCount];

        int[] lTypes = getTypes();
        for (int index = 0; index < typeCount; index++) {
            this.types[index] = lTypes[index];
            this.layout[index] = getLayoutId(this.types[index]);
        }
    }

    /**
     * 获取 item 类型的总数
     *
     * @return
     */
    protected abstract int getTypeCount();

    /**
     * 获取某一个类型对应的 LayoutId
     *
     * @param type
     * @return
     */
    protected abstract int getLayoutId(int type);

    /**
     * 获取item的所有类型，用 int【】 数组返回
     *
     * @return
     */
    protected abstract int[] getTypes();
}
