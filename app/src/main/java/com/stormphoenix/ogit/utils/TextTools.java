package com.stormphoenix.ogit.utils;

import android.content.ActivityNotFoundException;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

import com.stormphoenix.ogit.utils.HtmlUtils;
import com.umeng.analytics.MobclickAgent;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by StormPhoenix on 17-3-9.
 * StormPhoenix is a intelligent Android developer.
 */

public class TextTools {
    public static final LinkMovementMethod CHECKING_LINK_METHOD = new LinkMovementMethod() {
        @Override
        public boolean onTouchEvent(@NonNull TextView widget,
                                    @NonNull Spannable buffer, @NonNull MotionEvent event) {
            try {
                return super.onTouchEvent(widget, buffer, event);
            } catch (ActivityNotFoundException e) {
                MobclickAgent.reportError(widget.getContext(), e);
                return true;
            }
        }
    };

    // 从Link响应头之中获取某一個列表中item的数量
    public static int parseListCount(String raw) {
        // <https://api.github.com/user/6383426/starred?per_page=1&page=86>;rel="last"
        int begin = raw.lastIndexOf("page") + 5;
        int end = raw.lastIndexOf(">");
        return Integer.parseInt(raw.substring(begin, end));
    }

    public static void showReadmeHtml(TextView mTextReadme, String readmeText, Html.ImageGetter imageGetter) {
        Observable.just(readmeText)
                .map((Func1) (s) -> {
                    return transformHtml(readmeText, imageGetter);
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Spanned>() {
                    @Override
                    public void call(Spanned spanned) {
                        mTextReadme.setText(spanned);
                        mTextReadme.setMovementMethod(CHECKING_LINK_METHOD);
                    }
                });
    }

    private static Spanned transformHtml(String html, Html.ImageGetter imageGetter) {
        if (html == null) {
            html = "";
        }
        html = HtmlUtils.format(html);
        Spanned spanned = Html.fromHtml(html, imageGetter,
                HtmlUtils.getTagHandler());
        return spanned;
    }
}
