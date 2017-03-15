package com.stormphoenix.ogit.mvp.view;

import com.stormphoenix.ogit.mvp.view.base.BaseUIView;
import com.stormphoenix.ogit.widget.ImageHorizontalKeyValueLabel;
import com.stormphoenix.ogit.widget.ImageVerticalKeyValueLabel;

/**
 * Created by StormPhoenix on 17-3-14.
 * StormPhoenix is a intelligent Android developer.
 */

public interface OrgDetailsView extends BaseUIView {
    void addBaseLabel(ImageVerticalKeyValueLabel label);

    void addDynamicLabel(ImageHorizontalKeyValueLabel label);

    void setMembersCount(String memberCount);

    void setFollowersCount(String followersCount);

    void setFolloweringCount(String followeringCount);

    void setTitle(String title);

    void loadOrgHeaderImage(String url);
}
