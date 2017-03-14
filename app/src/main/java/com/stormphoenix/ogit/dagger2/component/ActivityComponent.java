package com.stormphoenix.ogit.dagger2.component;

import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.ui.activities.LoginActivity;
import com.stormphoenix.ogit.mvp.ui.activities.MainActivity;
import com.stormphoenix.ogit.mvp.ui.activities.OrgDetailsActivity;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.mvp.ui.activities.UserDetailsActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.CodeFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.ContributorsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.EventsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.FoldsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.OrgFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.StaredFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.search.SearchRepoFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.search.SearchUsersFragment;

import dagger.Component;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

@Component(modules = {ContextModule.class})
public interface ActivityComponent {
    void inject(OrgDetailsActivity activity);

    void inject(SearchUsersFragment fragment);

    void inject(OrgFragment fragment);

    void inject(SearchRepoFragment fragment);

    void inject(CodeFragment fragment);

    void inject(ContributorsFragment fragment);

    void inject(FoldsFragment fragment);

    void inject(StaredFragment fragment);

    void inject(MainActivity activity);

    void inject(UserDetailsActivity activity);

    void inject(RepositoryActivity activity);

    void inject(EventsFragment fragment);

    void inject(LoginActivity activity);
}
