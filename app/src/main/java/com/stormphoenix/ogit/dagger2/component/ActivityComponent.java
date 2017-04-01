package com.stormphoenix.ogit.dagger2.component;

import com.stormphoenix.ogit.dagger2.module.ContextModule;
import com.stormphoenix.ogit.mvp.ui.activities.LoginActivity;
import com.stormphoenix.ogit.mvp.ui.activities.MainActivity;
import com.stormphoenix.ogit.mvp.ui.activities.OrgProfileActivity;
import com.stormphoenix.ogit.mvp.ui.activities.RepositoryActivity;
import com.stormphoenix.ogit.mvp.ui.activities.UserProfileActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.CodeFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.commits.CommitDetailsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.OrganizationsFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.search.SearchRepoFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.search.SearchUsersFragment;

import dagger.Component;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

@Component(modules = {ContextModule.class})
public interface ActivityComponent {
    void inject(OrgProfileActivity activity);

    void inject(SearchUsersFragment fragment);

    void inject(OrganizationsFragment fragment);

    void inject(SearchRepoFragment fragment);

    void inject(CodeFragment fragment);

    void inject(CommitDetailsFragment fragment);

    void inject(MainActivity activity);

    void inject(UserProfileActivity activity);

    void inject(RepositoryActivity activity);

    void inject(LoginActivity activity);
}
