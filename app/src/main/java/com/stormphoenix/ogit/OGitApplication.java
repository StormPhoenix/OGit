package com.stormphoenix.ogit;

import android.app.Application;
import android.content.Context;

import com.stormphoenix.ogit.shares.OGitImageLoader;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class OGitApplication extends Application {
    public static Context instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initImageLoader();
    }

    private void initImageLoader() {
        OGitImageLoader.getInstance().init(this);
    }
}
