package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.ogit.mvp.ui.component.BreadcrumbView;
import com.stormphoenix.ogit.mvp.view.base.ListItemView;

/**
 * Created by StormPhoenix on 17-3-4.
 * StormPhoenix is a intelligent Android developer.
 */

public interface TreeItemView<T> extends ListItemView<T> {
    void addBreadcrumb(BreadcrumbView.Breadcrumb crumb);

    String getAbsolutPath();
}
