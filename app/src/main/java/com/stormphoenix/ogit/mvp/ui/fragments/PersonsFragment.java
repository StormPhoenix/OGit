package com.stormphoenix.ogit.mvp.ui.fragments;

import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.adapters.GitUserAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.base.PersonsPresenter;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

import java.util.ArrayList;

/**
 * Created by StormPhoenix on 17-3-1.
 * StormPhoenix is a intelligent Android developer.
 */

public class PersonsFragment extends ListWithPresenterFragment<GitUser> {
    private PersonsPresenter presenter;

    public static BaseFragment newInstance(PersonsPresenter personsPresenter) {
        PersonsFragment personsFragment = new PersonsFragment();
        personsFragment.setPresenter(personsPresenter);
        return personsFragment;
    }

    public void setPresenter(PersonsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public BaseRecyclerAdapter<GitUser> getAdapter() {
        return new GitUserAdapter(getActivity(), new ArrayList<>());
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return presenter;
    }

    @Override
    public void initializeInjector() {
    }
}
