package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.ogit.mvp.view.BaseView;

import java.util.List;

/**
 * Created by StormPhoenix on 17-2-28.
 * StormPhoenix is a intelligent Android developer.
 */
public interface ListItemView<T> extends BaseView {
    void initListItemView();

    void initRefreshLayout();

    void loadNewlyListItem(List<T> listItems);

    void startRefresh();

    void stopRefresh();

    int getListItemCounts();

    void loadMoreListItem(List<T> listItems);

    void showMessage(String message);

    void reLogin();
}
