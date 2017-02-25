package com.stormphoenix.ogit.shares;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by StormPhoenix on 17-2-25.
 * StormPhoenix is a intelligent Android developer.
 */

public class OGitImageLoader {
    private static OGitImageLoader instance = null;

    private OGitImageLoader() {
    }

    public static OGitImageLoader getInstance() {
        if (instance == null) {
            synchronized (OGitImageLoader.class) {
                if (instance == null) {
                    instance = new OGitImageLoader();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(config);
    }

    public void displayImage(String imageUrl, ImageView imageView, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }
}
