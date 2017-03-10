package com.stormphoenix.ogit;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.stormphoenix.ogit.utils.ImageUtils;

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initImageLoader() {
        ImageUtils.getInstance().init(this);
    }
}
