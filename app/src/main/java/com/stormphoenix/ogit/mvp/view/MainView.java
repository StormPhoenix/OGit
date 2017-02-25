package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.ogit.mvp.ui.fragments.BaseFragment;

import java.util.List;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public interface MainView extends BaseView {
    void initToolbar(String title);

    void initDrawerView();

    void initMainPagerFragments(String[] titles, List<BaseFragment> fragments);
}
