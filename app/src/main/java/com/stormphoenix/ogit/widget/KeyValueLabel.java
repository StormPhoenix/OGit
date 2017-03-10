package com.stormphoenix.ogit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stormphoenix.ogit.R;

/**
 * Created by StormPhoenix on 17-3-10.
 * StormPhoenix is a intelligent Android developer.
 */

public class KeyValueLabel extends RelativeLayout {
    public final static String TAG = KeyValueLabel.class.getSimpleName();

    private String keyName = "key";
    private String valueName = "value";
    private TextView textViewKey;
    private TextView textViewValue;

    public KeyValueLabel(Context context) {
        this(context, null);
    }

    public KeyValueLabel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyValueLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.key_value_label);
        keyName = a.getString(R.styleable.key_value_label_key_text);
        valueName = a.getString(R.styleable.key_value_label_value_text);

        LayoutInflater.from(context).inflate(R.layout.wiget_key_value_label, this);
        textViewKey = (TextView) findViewById(R.id.text_key);
        textViewValue = (TextView) findViewById(R.id.text_value);
        textViewKey.setText(keyName);
        textViewValue.setText(valueName);
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
