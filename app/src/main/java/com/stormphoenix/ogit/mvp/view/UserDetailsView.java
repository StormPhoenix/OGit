package com.stormphoenix.ogit.mvp.view;

import android.widget.ImageView;

import com.stormphoenix.ogit.mvp.view.base.BaseView;

/**
 * Created by StormPhoenix on 17-3-5.
 * StormPhoenix is a intelligent Android developer.
 */

public interface UserDetailsView extends BaseView {
    void setUpToolbar(String title);

    void setFollowers(String followers);

    void setFollowings(String followings);

    void setEmail(String email);

    void setLocation(String location);

    void setJoinTime(String joinTime);

    void stopProgress();

    void showProgress();

    void showMessage(String message);

    ImageView getHeadImageView();

    void setStaredCount(String count);
}
