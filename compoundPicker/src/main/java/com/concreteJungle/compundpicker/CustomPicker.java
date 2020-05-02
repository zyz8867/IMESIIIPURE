package com.concreteJungle.compundpicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by PeitaoYe on 2018/4/28.
 */

public class CustomPicker {

    public final static int DATE_MODE = 0;
    public final static int RANGE_MODE = 1;
    public final static int TIME_MODE = 3;
    public final static int DATE_TIME_MODE = 4;
    private int mPicker_mode;
    private Dialog mDialog;
    private Window mWindow;
    private Context mContext;

    private int min_year = 1900;
    private int max_year;

    private Calendar tempCalendar;
    private View mView;

    private NumberPicker year_picker;
    private NumberPicker month_picker;
    private NumberPicker day_picker;

    private int mSelected_year;
    private int mSelected_month;
    private int mSelected_day;

    private NumberPicker from_picker;
    private NumberPicker to_picker;

    private NumberPicker hour_picker;
    private NumberPicker min_picker;
    private Button cancle_button;
    private Button confirm_button;

    private int view_id;
    private OnConfirmListener mOnConfirmListener;
    private OnCancelListener onCancelListener;
    private String[] months = new String[]{"01月", "02月", "03月", "04月", "05月", "06月", "07月", "08月", "09月", "10月", "11月", "12月"};

    public CustomPicker(Context context, int mode) {
        mContext = context;
        mDialog = new Dialog(mContext);
        mWindow = mDialog.getWindow();
        tempCalendar = Calendar.getInstance();
        max_year = tempCalendar.get(Calendar.YEAR);
        this.mPicker_mode = mode;
        initDialog(mode);
    }

    private View getLayoutResourceView(int layoutResource) {
        return LayoutInflater.from(mContext).inflate(layoutResource, null);
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    private void initDialog(int mode) {
        switch (mode) {
            case DATE_MODE:
                mView = getLayoutResourceView(R.layout.date_picker);
                mDialog.setContentView(mView);
                mDialog.setCanceledOnTouchOutside(true);
                mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                mWindow.setWindowAnimations(R.style.bottom_pop);
                mWindow.setGravity(Gravity.BOTTOM);
                initDatePicker();
                break;
            case RANGE_MODE:
                mView = getLayoutResourceView(R.layout.range_picker);
                mDialog.setContentView(mView);
                mDialog.setCanceledOnTouchOutside(true);
                mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                mWindow.setWindowAnimations(R.style.bottom_pop);
                mWindow.setGravity(Gravity.BOTTOM);
                initRangePicker();
                break;
            case TIME_MODE:
                mView = getLayoutResourceView(R.layout.time_picker);
                mDialog.setContentView(mView);
                mDialog.setCanceledOnTouchOutside(true);
                mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                mWindow.setWindowAnimations(R.style.bottom_pop);
                mWindow.setGravity(Gravity.BOTTOM);
                initTimePicker();
                break;
            case DATE_TIME_MODE:
                mView = getLayoutResourceView(R.layout.date_time_picker);
                mDialog.setContentView(mView);
                mDialog.setCanceledOnTouchOutside(true);
                mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                mWindow.setWindowAnimations(R.style.bottom_pop);
                mWindow.setGravity(Gravity.BOTTOM);
                initDateTimePicker();
                break;
            default:

                break;
        }

    }

    private void initDateTimePicker() {
        initDatePicker();
        hour_picker = mView.findViewById(R.id.datePicker4);
        min_picker = mView.findViewById(R.id.datePicker5);

        String[] range_set1 = new String[24];
        for (int i = 0; i <= 23; i++) {
            range_set1[i] = String.format("%s时", i);
        }

        int cur_hour = tempCalendar.get(Calendar.HOUR_OF_DAY);//24小时制
        int cur_min = tempCalendar.get(Calendar.MINUTE);

        hour_picker.setMinValue(0);
        hour_picker.setMaxValue(23);
        hour_picker.setValue(cur_hour);
        hour_picker.setDisplayedValues(range_set1);
        String[] range_set2 = new String[60];
        for (int i = 0; i <= 59; i++) {
            range_set2[i] = String.format("%s分", i);
        }
        min_picker.setMinValue(0);
        min_picker.setMaxValue(59);
        min_picker.setValue(cur_min);
        min_picker.setDisplayedValues(range_set2);
    }

    private void initTimePicker() {
        hour_picker = mView.findViewById(R.id.hour_picker);
        min_picker = mView.findViewById(R.id.min_picker);

        Button b_cancel = mView.findViewById(R.id.cancel);
        Button b_confirm = mView.findViewById(R.id.confirm);

        b_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnConfirmListener != null) {
                    mOnConfirmListener.onConfirm(view_id);
                }
                mDialog.dismiss();
            }
        });

        String[] range_set1 = new String[24];
        for (int i = 0; i <= 23; i++) {
            range_set1[i] = String.format("%s时", i);
        }

        hour_picker.setMinValue(0);
        hour_picker.setMaxValue(23);
        hour_picker.setValue(20);
        hour_picker.setDisplayedValues(range_set1);
        String[] range_set2 = new String[60];
        for (int i = 0; i <= 59; i++) {
            range_set2[i] = String.format("%s分", i);
        }
        min_picker.setMinValue(0);
        min_picker.setMaxValue(59);
        min_picker.setValue(50);
        min_picker.setDisplayedValues(range_set2);

    }

    private void initRangePicker() {
        from_picker = (NumberPicker) mView.findViewById(R.id.from_picker);
        to_picker = (NumberPicker) mView.findViewById(R.id.to_picker);

        Button b_cancel = (Button) mView.findViewById(R.id.cancel);
        Button b_confirm = (Button) mView.findViewById(R.id.confirm);

        b_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                if (onCancelListener != null) {
                    onCancelListener.onCancel(view_id);
                }
            }
        });

        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnConfirmListener != null) {
                    mOnConfirmListener.onConfirm(view_id);
                }
                mDialog.dismiss();
            }
        });

        String[] range_set = new String[99];
        for (int i = 1; i <= 99; i++) {
            range_set[i - 1] = String.format("%s", i);
        }

        from_picker.setMinValue(1);
        from_picker.setMaxValue(99);
        from_picker.setValue(20);
        from_picker.setDisplayedValues(range_set);

        to_picker.setMinValue(1);
        to_picker.setMaxValue(99);
        to_picker.setValue(50);
        to_picker.setDisplayedValues(range_set);


    }

    private void initDatePicker() {
        int cur_year = tempCalendar.get(Calendar.YEAR);
        int cur_month = tempCalendar.get(Calendar.MONTH);
        int cur_day = tempCalendar.get(Calendar.DAY_OF_MONTH);
        int years_count = max_year - min_year + 1;
        year_picker = mView.findViewById(R.id.datePicker1);
        year_picker.setMinValue(min_year);
        year_picker.setMaxValue(max_year);
        changeDividerColor(year_picker, Color.GRAY);
        String[] years_dataset = getYearsDataset(years_count);
        year_picker.setDisplayedValues(years_dataset);
        year_picker.setValue(cur_year);
        year_picker.setWrapSelectorWheel(false);
        year_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                emptyDataset(day_picker);

                int daysOfMonth = daysInMonth(newVal, month_picker.getValue());
                final String[] days_dataset = getDaysDataset(newVal, month_picker.getValue());

                day_picker.setMinValue(1);
                day_picker.setMaxValue(daysOfMonth);
                day_picker.setDisplayedValues(days_dataset);
                day_picker.setValue(1);

            }
        });

        month_picker = mView.findViewById(R.id.datePicker2);
        changeDividerColor(month_picker, Color.GRAY);
        month_picker.setMaxValue(11);
        month_picker.setMinValue(0);
        month_picker.setDisplayedValues(months);
        month_picker.setValue(cur_month);
        month_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                emptyDataset(day_picker);
                int daysOfMonth = daysInMonth(year_picker.getValue(), newVal);
                final String[] days_dataset = getDaysDataset(year_picker.getValue(), newVal);

                day_picker.setDisplayedValues(days_dataset);
                day_picker.setMinValue(1);
                day_picker.setMaxValue(daysOfMonth);
                day_picker.setValue(1);

            }
        });

        int daysOfMonth = daysInMonth(cur_year - 20, cur_month);
        final String[] days_dataset = getDaysDataset(cur_year - 20, cur_month);
        day_picker = mView.findViewById(R.id.datePicker3);
        changeDividerColor(day_picker, Color.GRAY);
        day_picker.setDisplayedValues(days_dataset);
        day_picker.setMinValue(1);
        day_picker.setMaxValue(daysOfMonth);
        day_picker.setValue(cur_day);


        cancle_button = mView.findViewById(R.id.cancel);
        cancle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelListener != null) {
                    onCancelListener.onCancel(view_id);
                }
                mDialog.dismiss();
            }
        });
        confirm_button = mView.findViewById(R.id.confirm);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnConfirmListener != null) {
                    mOnConfirmListener.onConfirm(view_id);
                }

                mDialog.dismiss();
            }
        });
    }


    public int getSelected_year() {
        mSelected_year = year_picker.getValue();
        return mSelected_year;
    }


    public int getSelected_month() {
        mSelected_month = month_picker.getValue();
        return mSelected_month;
    }

    public int getSelected_day() {
        mSelected_day = day_picker.getValue();
        return mSelected_day;
    }

    public int getFromValue() {
        return from_picker.getValue();
    }

    public int getToValue() {
        return to_picker.getValue();
    }

    public int getHour() {
        return hour_picker.getValue();
    }

    public int getMin() {
        return min_picker.getValue();
    }

    public int getSec() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    public void show(int view_id) {
        mDialog.show();
        this.view_id = view_id;
    }

    private String[] getYearsDataset(int years) {
        String[] yearsDataset = new String[years];
        for (int i = 0; i < years; i++) {
            int year = i + min_year;
            yearsDataset[i] = year + "年";
        }
        return yearsDataset;
    }

    private String[] getDaysDataset(int year, int month) {
        int days_count = daysInMonth(year, month);
        String[] daysDataset = new String[days_count];
        for (int i = 1; i <= days_count; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            if (i < 10) {
                daysDataset[i - 1] = stringBuilder.append("0").append(i).append("日").toString();
            } else {
                daysDataset[i - 1] = stringBuilder.append(i).append("日").toString();
            }

        }
        return daysDataset;
    }

    private int daysInMonth(int year, int month) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return (year % 4 == 0) ? 29 : 28;
            default:
                throw new IllegalArgumentException("invalid month params");
        }
    }


    private void emptyDataset(NumberPicker numberPicker) {
        numberPicker.setDisplayedValues(null);
        numberPicker.setMaxValue(0);
        numberPicker.setMinValue(0);
        numberPicker.setFormatter(null);


    }

    public void setOnConfirmListener(OnConfirmListener l) {
        mOnConfirmListener = l;
    }

    public interface OnConfirmListener {
        void onConfirm(int view_id);
    }

    public interface OnCancelListener {
        void onCancel(int view_id);
    }


    /**
     * using reflection to change the selector_date of divider
     *
     * @param numberPicker
     */
    private void changeDividerColor(NumberPicker numberPicker, int color) {
        Field[] fields = numberPicker.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("mSelectionDivider")) {
                field.setAccessible(true);
                try {
                    field.set(numberPicker, new ColorDrawable(color));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


}
