package com.stormphoenix.ogit.mvp.view;

import android.widget.ImageView;

import com.stormphoenix.ogit.mvp.view.base.OwnerDetailsView;

/**
 * Created by StormPhoenix on 17-3-5.
 * StormPhoenix is a intelligent Android developer.
 */

public interface UserDetailsView extends OwnerDetailsView {
    void showMessage(String message);

    void setStaredCount(String count);

    void setIsFollow(boolean isFollow);
}
