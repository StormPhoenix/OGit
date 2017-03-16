package com.stormphoenix.ogit.mvp.view.base;

import com.stormphoenix.ogit.mvp.view.base.BaseView;

import java.util.List;

/**
 * Created by StormPhoenix on 17-2-28.
 * StormPhoenix is a intelligent Android developer.
 */
public interface ListItemView<T> extends BaseUIView {
    void initListItemView();

    void initRefreshLayout();

    void loadNewlyListItem(List<T> listItems);

    int getListItemCounts();

    void loadMoreListItem(List<T> listItems);

    void reLogin();

    void clearAllItems();
}
