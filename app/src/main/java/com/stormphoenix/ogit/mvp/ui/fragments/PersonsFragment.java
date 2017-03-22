package com.stormphoenix.ogit.mvp.ui.fragments;

import android.view.View;

import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitUserAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.base.PersonsPresenter;
import com.stormphoenix.ogit.mvp.presenter.list.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.UserProfileActivity;
import com.stormphoenix.ogit.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.ogit.mvp.ui.fragments.base.ListWithPresenterFragment;

import org.greenrobot.eventbus.EventBus;

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
        GitUserAdapter adapter = new GitUserAdapter(getActivity(), new ArrayList<>());
        adapter.addOnViewClickListener(R.id.owner_wrapper, new BaseRecyclerAdapter.OnInternalViewClickListener<GitUser>() {
            @Override
            public void onClick(View parentV, View v, Integer position, GitUser values) {
                EventBus.getDefault().postSticky(values);
                getActivity().startActivity(UserProfileActivity.getIntent(getActivity()));
            }

            @Override
            public boolean onLongClick(View parentV, View v, Integer position, GitUser values) {
                return false;
            }
        });
        return adapter;
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return presenter;
    }

    @Override
    public void initializeInjector() {
    }
}
