package com.stormphoenix.ogit.shares;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.stormphoenix.ogit.R;
import com.stormphoenix.ogit.utils.ImageUtils;
import com.stormphoenix.ogit.utils.SystemUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Integer.MAX_VALUE;


public class HtmlImageGetter implements Html.ImageGetter {

    private static class LoadingImageGetter implements Html.ImageGetter {

        private final Drawable image;

        private LoadingImageGetter(final Context context, final int size) {
            int imageSize = SystemUtils.getIntPixels(context, size);
            image = context.getResources().getDrawable(R.drawable.ic_loading_img);
            image.setBounds(0, 0, imageSize, imageSize);
        }

        @Override
        public Drawable getDrawable(String source) {
            return image;
        }
    }

    private final LoadingImageGetter loading;

    private final String TAG = getClass().getName();

    private Context context;
    private TextView container;
    private String baseUrl;

    private File fileDir;
    private int width;
    private int height;

    public static final int maxMemory = (int) OGitConstants.MAX_MEMORY / 1024;
    private static final LruCache<String, File> cache = new LruCache<String, File>(maxMemory / 8) {
        @Override
        protected int sizeOf(String key, File value) {
            return (int) (value.length() / 1024);
        }
    };

    public HtmlImageGetter(TextView t, Context c, String baseUrl) {
        this.context = c;
        this.container = t;
        this.baseUrl = baseUrl;
        loading = new LoadingImageGetter(context, 24);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Point size;
        if (Build.VERSION.SDK_INT < 13) {
            Display display = wm.getDefaultDisplay();
            size = new Point(display.getWidth(), display.getHeight());
        } else {
            Point p = new Point();
            wm.getDefaultDisplay().getSize(p);
            size = p;
        }
        fileDir = context.getCacheDir();
        width = size.x;
        height = size.y;
    }

    @Override
    public Drawable getDrawable(String source) {
        try {
            Request request = new Request.Builder().url(source).build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();

            Bitmap bitmap = ImageUtils.getBitmap(response.body().bytes(), width, MAX_VALUE);
            if (bitmap == null)
                return loading.getDrawable(source);
            BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            return drawable;
        } catch (IOException e) {
            return loading.getDrawable(source);
        } catch (Exception e) {
            return loading.getDrawable(source);
        }
    }
}
