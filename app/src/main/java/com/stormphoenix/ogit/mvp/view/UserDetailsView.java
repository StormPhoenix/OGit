package com.stormphoenix.ogit.mvp.view;

import android.widget.ImageView;

import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.mvp.view.base.BaseView;
import com.stormphoenix.ogit.widget.ImageHorizontalKeyValueLabel;
import com.stormphoenix.ogit.widget.ImageVerticalKeyValueLabel;

/**
 * Created by StormPhoenix on 17-3-5.
 * StormPhoenix is a intelligent Android developer.
 */

public interface UserDetailsView extends BaseUIView {
    void addBaseLabel(ImageVerticalKeyValueLabel label);

    void setUpToolbar(String title);

    void setFollowers(String followers);

    void setFollowings(String followings);

    void stopProgress();

    void showProgress();

    void showMessage(String message);

    ImageView getHeadImageView();

    void setStaredCount(String count);

    void addDynamicLabel(ImageHorizontalKeyValueLabel dynamicLabel);

    void setIsFollow(boolean isFollow);
}
