package com.stormphoenix.ogit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stormphoenix.ogit.R;

/**
 * Created by StormPhoenix on 17-3-10.
 * StormPhoenix is a intelligent Android developer.
 */

public class ImageVerticalKeyValueLabel extends RelativeLayout {
    public final static String TAG = ImageVerticalKeyValueLabel.class.getSimpleName();

    private String keyName = "key";
    private String valueName = "value";
    private Drawable drawable;

    private ImageView image;
    private TextView textViewKey;
    private TextView textViewValue;

    public ImageVerticalKeyValueLabel(Context context) {
        this(context, null);
    }

    public ImageVerticalKeyValueLabel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageVerticalKeyValueLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.key_value_label);
        keyName = a.getString(R.styleable.key_value_label_key_text);
        valueName = a.getString(R.styleable.key_value_label_value_text);
        drawable = a.getDrawable(R.styleable.key_value_label_image);

        LayoutInflater.from(context).inflate(R.layout.widget_img_key_value_label_vertical, this);
        textViewKey = (TextView) findViewById(R.id.text_key);
        textViewValue = (TextView) findViewById(R.id.text_value);
        image = (ImageView) findViewById(R.id.image);

        image.setImageDrawable(drawable);
        textViewKey.setText(keyName);
        textViewValue.setText(valueName);
    }

    public void setImageDrawableResourceId(int resId) {
        image.setImageResource(resId);
    }

    public void setValueName(String valueName) {
        textViewValue.setText(valueName);
        invalidate();
    }

    public void setKeyName(String keyName) {
        textViewKey.setText(keyName);
        invalidate();
    }
}
