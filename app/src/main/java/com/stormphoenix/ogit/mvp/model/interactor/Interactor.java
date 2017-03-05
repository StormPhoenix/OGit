package com.stormphoenix.ogit.mvp.model.interactor;

import android.content.Context;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class Interactor {
    private Context mContext = null;

    public Interactor(Context context) {
        mContext = context;
    }
}
