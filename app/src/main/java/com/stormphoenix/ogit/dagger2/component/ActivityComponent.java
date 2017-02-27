package com.stormphoenix.ogit.dagger2.component;

import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.ui.activities.LoginActivity;
import com.stormphoenix.ogit.mvp.ui.activities.MainActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.EventsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.StarredFragment;

import dagger.Component;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

@Component(modules = {ContextModule.class})
public interface ActivityComponent {
    void inject(StarredFragment fragment);
    void inject(MainActivity activity);
    void inject(EventsFragment fragment);
    void inject(LoginActivity activity);
}
