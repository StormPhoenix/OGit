package com.stormphoenix.ogit.mvp.ui.dialog;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.stormphoenix.ogit.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Developer on 17-1-20.
 * Wang Cheng is a intelligent Android developer.
 */

public class TimeSelectorDialogGenerator {
    private TimePickerDialog.Builder builder = null;
    private TimePickerDialog dialog = null;

    private Context mContext = null;

    public TimeSelectorDialogGenerator(Context context, OnDateSetListener listener) {
        mContext = context;
        builder = new TimePickerDialog.Builder();
        builder.setCallBack(listener)
                .setCancelStringId(context.getResources().getString(R.string.picker_cancel))
                .setSureStringId(context.getResources().getString(R.string.picker_sure))
                .setTitleStringId(context.getResources().getString(R.string.picker_title))
                .setYearText(context.getResources().getString(R.string.picker_year))
                .setMonthText(context.getResources().getString(R.string.picker_month))
                .setDayText(context.getResources().getString(R.string.picker_day))
                .setHourText(context.getResources().getString(R.string.picker_hour))
                .setMinuteText(context.getResources().getString(R.string.picker_minute))
                .setCyclic(false)
//                .setMinMillseconds(System.currentTimeMillis())
//                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(context.getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(context.getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(context.getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12);
    }

    public TimeSelectorDialogGenerator setType(Type type) {
        builder.setType(type);
        return this;
    }

    public void show(FragmentManager fragmentManager) {
        dialog = builder.build();
        dialog.show(fragmentManager, "TimePicker");
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public static abstract class DefaultTimeSetListener implements OnDateSetListener {
        private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        protected String fomatedTimeText;

        @Override
        public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
            fomatedTimeText = formater.format(new Date(millseconds));
            timePickerView.dismiss();
        }
    }
}
