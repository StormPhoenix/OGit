package com.stormphoenix.ogit.adapters.base;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.ogit.adapters.base.MultiTypeAdapter.MultiTypeViewHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by StormPhoenix on 17-3-28.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class MultiTypeAdapter extends TypeAdapter<MultiTypeAdapter.Item, MultiTypeViewHolder> {
    public static final String TAG = MultiTypeAdapter.class.getSimpleName();
    private final LayoutInflater inflater;
    // 存放所有的数据
//    private final List<MultiTypeAdapter.Item> items;
    // 存放所有的ViewType类型
    private final Map<Integer, MultiTypeAdapter.ViewType> viewTypeMap;
    private final Map<Integer, View> viewPosMap;

    @Override
    protected Animator[] getAnimators(View itemView) {
        return new Animator[0];
    }

    public MultiTypeAdapter(Activity activity) {
        this(activity, activity.getLayoutInflater());
    }

    public MultiTypeAdapter(Context context) {
        this(context, LayoutInflater.from(context));
    }

    public MultiTypeAdapter(Context context, LayoutInflater inflater) {
        super(context, new LinkedList<>());
        this.inflater = inflater;
        int[] viewItemTypes = getItemTypes();
        viewTypeMap = new HashMap<>();
        viewPosMap = new HashMap<>();
        int[] empty = new int[0];

        for (int i = 0; i < viewItemTypes.length; i++) {
            int type = viewItemTypes[i];
            int layoutId = getLayoutId(type);
            int[] layoutChildrenIds = getChildrentIds(type);

            if (layoutChildrenIds == null) {
                layoutChildrenIds = empty;
            }

            MultiTypeAdapter.ViewType viewType = new MultiTypeAdapter.ViewType(type, layoutId, layoutChildrenIds);
            viewTypeMap.put(new Integer(type), viewType);
        }
    }

    public MultiTypeAdapter clear() {
        this.data.clear();
        this.notifyDataSetChanged();
        return this;
    }

    /**
     * 获取到所有类型对应的 View 的子View的id
     *
     * @param type
     * @return
     */
    protected abstract int[] getChildrentIds(int type);

    public MultiTypeAdapter addItem(int type, Object item) {
        this.data.add(new MultiTypeAdapter.Item(type, item));
        this.notifyDataSetChanged();
        return this;
    }

    public MultiTypeAdapter addItems(int type, Object[] items) {
        if (items != null && items.length != 0) {
            int len = items.length;

            for (int i = 0; i < len; i++) {
                Object item = items[i];
                this.data.add(new MultiTypeAdapter.Item(type, item));
            }

            this.notifyDataSetChanged();
            return this;
        } else {
            return this;
        }
    }

    public MultiTypeAdapter addItems(int type, Collection<?> items) {
        if (items != null && !items.isEmpty()) {
            Iterator iterator = items.iterator();

            while (iterator.hasNext()) {
                Object item = iterator.next();
                this.data.add(new MultiTypeAdapter.Item(type, item));
            }

            this.notifyDataSetChanged();
            return this;
        } else {
            return this;
        }
    }

    public MultiTypeAdapter removeItem(int position) {
        if (position > 0 && position < this.data.size() && this.data.remove(position) != null) {
            this.notifyDataSetChanged();
        }
        return this;
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    @Override
    public MultiTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = this.initialize(viewType, this.inflater.inflate(viewTypeMap.get(viewType).layout, null));
        MultiTypeViewHolder viewHolder = new MultiTypeViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MultiTypeViewHolder holder, int position) {
        View itemView = holder.itemView;
        this.update(position, itemView, this.getItem(position), getItemViewType(position));
    }

    public Object getItem(int position) {
        return this.data.get(position).item;
    }

    @Override
    public long getItemId(int position) {
        return (long) this.data.get(position).hashCode();
    }

    /**
     * 获取到所有类型对应的 View 的id
     *
     * @param type
     * @return
     */
    protected abstract int getLayoutId(int type);

    /**
     * 获取所有的View的类型
     *
     * @return
     */
    protected abstract int[] getItemTypes();

    /**
     * 获取position位置的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return this.data.get(position).type;
    }

    protected void update(int position, View view, Object item, int type) {
        Log.e(TAG, "update: " + Integer.toHexString(view.getId()) + " " + type);
        this.setCurrentView(view);
        this.update(position, item, type);
    }

    /**
     * 当 update(int, Object, int) 被调用的时候，可以保证这个 position 对应的itemView
     * 就是本函数里面操作的 View。
     * update(int, Object, int) 函数用户当前的 itemView 进行更新。
     *
     * @param position
     * @param item
     * @param type
     */
    protected abstract void update(int position, Object item, final int type);

    @Override
    public void addAll(List<Item> models) {
        // 默认覆盖上层方法
    }

    @Override
    public void add(Item model) {
        // 默认覆盖上层方法
    }

    @Override
    public void swap(int srcIndex, int targetIndex) {
        // 默认覆盖上层方法
    }

    @Override
    public void update(Item model) {
        // 默认覆盖上层方法
    }

    @Override
    public void setData(List<Item> data) {
        // 默认覆盖上层方法
    }

    @Override
    public void remove(Item model) {
        // 默认覆盖上层方法
    }

    @Override
    public void update(Item model, int removedIndex) {
        // 默认覆盖上层方法
    }

    @Override
    public void update(Item model, int removedIndex, int insertIndex) {
        // 默认覆盖上层方法
    }

    protected View initialize(int type, View view) {
        return super.initialize(view, viewTypeMap.get(type).layoutChildrenIds);
    }

    public static class MultiTypeViewHolder extends RecyclerView.ViewHolder {

        public MultiTypeViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ViewType {
        public final int type;
        public final int layout;
        public final int[] layoutChildrenIds;

        public ViewType(int type, int layout, int[] layoutChildrenIds) {
            this.type = type;
            this.layout = layout;
            this.layoutChildrenIds = layoutChildrenIds;
        }
    }

    public static class Item {
        public final int type;
        public final Object item;

        private Item(int type, Object item) {
            this.type = type;
            this.item = item;
        }

        public int hashCode() {
            return this.item.hashCode();
        }
    }
}
