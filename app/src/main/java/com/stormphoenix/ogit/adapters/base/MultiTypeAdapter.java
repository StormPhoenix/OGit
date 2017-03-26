package com.stormphoenix.ogit.adapters.base;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by StormPhoenix on 17-3-26.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class MultiTypeAdapter extends TypeAdapter {
    private final LayoutInflater inflater;
    // 存放所有的数据
    private final List<Item> items;
    // 存放所有的ViewType类型
    private final Map<Integer, ViewType> viewTypeMap;

    public MultiTypeAdapter(Activity activity) {
        this(activity.getLayoutInflater());
    }

    public MultiTypeAdapter(Context context) {
        this(LayoutInflater.from(context));
    }

    public MultiTypeAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.items = new LinkedList<>();
        int[] viewItemTypes = getItemTypes();
        viewTypeMap = new HashMap<>();
        int[] empty = new int[0];

        for (int i = 0; i < viewItemTypes.length; i++) {
            int type = viewItemTypes[i];
            int layoutId = getLayoutId(type);
            int[] layoutChildrenIds = getChildrentIds(type);

            if (layoutChildrenIds == null) {
                layoutChildrenIds = empty;
            }

            ViewType viewType = new ViewType(type, layoutId, layoutChildrenIds);
            viewTypeMap.put(new Integer(type), viewType);
        }
    }

    public MultiTypeAdapter clear() {
        this.items.clear();
        this.notifyDataSetChanged();
        return this;
    }

    public MultiTypeAdapter addItem(int type, Object item) {
        this.items.add(new Item(type, item));
        this.notifyDataSetChanged();
        return this;
    }

    public MultiTypeAdapter addItems(int type, Object[] items) {
        if (items != null && items.length != 0) {
            int len = items.length;

            for (int i = 0; i < len; i++) {
                Object item = items[i];
                this.items.add(new Item(type, item));
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
                this.items.add(new Item(type, item));
            }

            this.notifyDataSetChanged();
            return this;
        } else {
            return this;
        }
    }

    public MultiTypeAdapter removeItem(int position) {
        if (position > 0 && position < this.items.size() && this.items.remove(position) != null) {
            this.notifyDataSetChanged();
        }

        return this;
    }

    public int getItemCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return ((Item) this.items.get(position).item);
    }

    public long getItemId(int position) {
        return (long) ((Item) this.items.get(position)).hashCode();
    }

    /**
     * 获取position位置的类型
     *
     * @param position
     * @return
     */
    public int getItemType(int position) {
        return this.items.get(position).type;
    }

    protected void update(int position, View view, Object item, int type) {
        this.setCurrentView(view);
        this.update(position, item, type);
    }

    protected View initialize(int type, View view) {
        return super.initialize(view, viewTypeMap.get(type).layoutChildrenIds);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = this.getItemType(position);
        if (convertView == null) {
            convertView = this.initialize(type, this.inflater.inflate(viewTypeMap.get(type).layout, null));
        }

        this.update(position, convertView, this.getItem(position), type);
        return convertView;
    }

    /**
     * 当 update(int, Object, int) 被调用的时候，可以保证这个 position 对应的itemView
     * 就是本函数里面操作的 View。
     * update(int, Object, int) 函数用户当前的 itemView 进行更新。
     * @param position
     * @param item
     * @param type
     */
    protected abstract void update(int position, Object item, final int type);

    /**
     * 获取所有的View的类型
     *
     * @return
     */
    protected abstract int[] getItemTypes();

    /**
     * 获取到所有类型对应的 View 的子View的id
     *
     * @param type
     * @return
     */
    protected abstract int[] getChildrentIds(int type);

    /**
     * 获取到所有类型对应的 View 的id
     *
     * @param type
     * @return
     */
    protected abstract int getLayoutId(int type);

    public int getCount() {
        return this.items.size();
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

    private static class Item {
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
