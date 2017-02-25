package com.stormphoenix.ogit.dagger2;

import com.stormphoenix.ogit.mvp.ui.fragments.EventsFragment;

import dagger.Component;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

@Component(modules = {})
public interface ActivityComponent {
    void inject(EventsFragment fragment);
}
