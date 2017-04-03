package com.stormphoenix.ogit.mvp.ui.fragments.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stormphoenix.httpknife.github.GitUser;
import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.adapters.GitUserAdapter;
import com.stormphoenix.ogit.adapters.base.BaseRecyclerAdapter;
import com.stormphoenix.ogit.mvp.presenter.base.OwnerPresenter;
import com.stormphoenix.ogit.mvp.presenter.base.ListItemPresenter;
import com.stormphoenix.ogit.mvp.ui.activities.UserProfileActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by StormPhoenix on 17-3-1.
 * StormPhoenix is a intelligent Android developer.
 */

public class UsersFragment extends ListWithPresenterFragment<GitUser> {
    private OwnerPresenter presenter;

    public static UsersFragment newInstance(OwnerPresenter ownerPresenter) {
        UsersFragment usersFragment = new UsersFragment();
        usersFragment.setPresenter(ownerPresenter);
        return usersFragment;
    }

    public void setPresenter(OwnerPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public BaseRecyclerAdapter<GitUser, RecyclerView.ViewHolder> getAdapter() {
        mAdapter = new GitUserAdapter(getActivity(), new ArrayList<>());
        mAdapter.addOnViewClickListener(R.id.owner_wrapper, new BaseRecyclerAdapter.OnInternalViewClickListener<GitUser>() {
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
        return mAdapter;
    }

    @Override
    public ListItemPresenter getListItemPresetner() {
        return presenter;
    }

    @Override
    public void initializeInjector() {
    }
}
