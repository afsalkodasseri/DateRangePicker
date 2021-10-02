package com.afsal.rangedialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PickerDialog {
    Context context;
    int selector=0;
    Date pickerStart,pickerEnd;
    SimpleDateFormat mdy,dm;
    DatePickerView dateRanger;
    public OnRangeSelect RangeSelector;
    int isSetRanger=0;

    int currentPickerDrw=R.drawable.custom_blue_rectangle;
    int prevPickerDrw=R.drawable.custom_grey_rectangle;
    int CrTextColor=Color.WHITE;
    int PrTextColor=Color.BLACK;

    int TodayDrw=R.drawable.date_square_color_out;
    int StartDrw=R.drawable.custom_date_start;
    int EndDrw=R.drawable.custom_date_end;
    int MidDrw=R.drawable.custom_date_range_p;
    int SingleDrw=R.drawable.date_square_color_blue;
    int TodayTxt= Color.parseColor("#000000");
    int StartTxt=Color.parseColor("#FFFFFF");
    int SingleTxt=Color.parseColor("#FFFFFF");
    int EndTxt=Color.parseColor("#FFFFFF");
    int MidTxt=Color.parseColor("#000000");

    Dialog datePickerDialog;
    TextView txtStartDate,txtEndDate;

    public PickerDialog(Context context)
    {
        this.context=context;
        pickerStart=new Date();
        pickerEnd=new Date();
        mdy=new SimpleDateFormat("MMMM dd,yy", Locale.ENGLISH);
        dm=new SimpleDateFormat("MMM dd", Locale.ENGLISH);

        selector=0;
        datePickerDialog=new Dialog(context);
        datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datePickerDialog.setContentView(R.layout.date_picker_dialog);
        datePickerDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.setCancelable(true);

        LinearLayout btnCancel=datePickerDialog.findViewById(R.id.btnCancel);
        LinearLayout btnApply=datePickerDialog.findViewById(R.id.btnChange);
        txtStartDate=datePickerDialog.findViewById(R.id.txtStartDate);
        TextView txtStartTitle=datePickerDialog.findViewById(R.id.txtStartTitle);
        txtEndDate=datePickerDialog.findViewById(R.id.txtEndDate);
        TextView txtEndTitle=datePickerDialog.findViewById(R.id.txtEndTitle);
        LinearLayout lyStartDate=datePickerDialog.findViewById(R.id.lyStartDate);
        LinearLayout lyEndDate=datePickerDialog.findViewById(R.id.lyEndDate);

        if(selector==0)
        {
            lyStartDate.setBackgroundResource(currentPickerDrw);
            txtStartTitle.setTextColor(CrTextColor);
            txtStartDate.setText(mdy.format(pickerStart));
            txtStartDate.setTextColor(CrTextColor);

            lyEndDate.setBackgroundResource(prevPickerDrw);
            txtEndTitle.setTextColor(PrTextColor);
            txtEndDate.setText(mdy.format(pickerEnd));
            txtEndDate.setTextColor(PrTextColor);

        }else{
            lyEndDate.setBackgroundResource(currentPickerDrw);
            txtEndTitle.setTextColor(CrTextColor);
            txtEndDate.setText(mdy.format(pickerEnd));
            txtEndDate.setTextColor(CrTextColor);

            lyStartDate.setBackgroundResource(prevPickerDrw);
            txtStartTitle.setTextColor(PrTextColor);
            txtStartDate.setText(mdy.format(pickerStart));
            txtStartDate.setTextColor(PrTextColor);
        }

        dateRanger=datePickerDialog.findViewById(R.id.dateRangerView);
        dateRanger.mDateSelector=new DatePickerView.DateSelector() {
            @Override
            public void onDateSelected() {
                if(selector==0)
                {
                    pickerStart=dateRanger.color_date;
                    txtStartDate.setText(mdy.format(pickerStart));
                    if(pickerStart.after(pickerEnd))
                    {
                        pickerEnd=pickerStart;
                        txtEndDate.setText(mdy.format(pickerEnd));
                    }
                    selector=1;

                    lyEndDate.setBackgroundResource(currentPickerDrw);
                    txtEndTitle.setTextColor(CrTextColor);
                    txtEndDate.setText(mdy.format(pickerEnd));
                    txtEndDate.setTextColor(CrTextColor);

                    lyStartDate.setBackgroundResource(prevPickerDrw);
                    txtStartTitle.setTextColor(PrTextColor);
                    txtStartDate.setText(mdy.format(pickerStart));
                    txtStartDate.setTextColor(PrTextColor);
                }else{
                    pickerEnd=dateRanger.color_date;
                    if(pickerEnd.before(pickerStart))
                    {
                        pickerStart=pickerEnd;
                        txtStartDate.setText(mdy.format(pickerStart));
                    }
                    txtEndDate.setText(mdy.format(pickerEnd));
                }
                dateRanger.setRangeDate(pickerStart,pickerEnd);
            }
        };

        lyStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=0;

                lyStartDate.setBackgroundResource(currentPickerDrw);
                txtStartTitle.setTextColor(CrTextColor);
                txtStartDate.setText(mdy.format(pickerStart));
                txtStartDate.setTextColor(CrTextColor);

                lyEndDate.setBackgroundResource(prevPickerDrw);
                txtEndTitle.setTextColor(PrTextColor);
                txtEndDate.setText(mdy.format(pickerEnd));
                txtEndDate.setTextColor(PrTextColor);
            }
        });

        lyEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=1;

                lyEndDate.setBackgroundResource(currentPickerDrw);
                txtEndTitle.setTextColor(CrTextColor);
                txtEndDate.setText(mdy.format(pickerEnd));
                txtEndDate.setTextColor(CrTextColor);

                lyStartDate.setBackgroundResource(prevPickerDrw);
                txtStartTitle.setTextColor(PrTextColor);
                txtStartDate.setText(mdy.format(pickerStart));
                txtStartDate.setTextColor(PrTextColor);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.dismiss();
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSetRanger==1)
                    RangeSelector.OnSelect(pickerStart,pickerEnd);

                datePickerDialog.dismiss();
            }
        });
    }
    public PickerDialog(Context context,Date Start,Date End)
    {
        this.context=context;
        pickerStart=Start;
        pickerEnd=End;
        mdy=new SimpleDateFormat("MMMM dd,yy", Locale.ENGLISH);
        dm=new SimpleDateFormat("MMM dd", Locale.ENGLISH);

        selector=0;
        datePickerDialog=new Dialog(context);
        datePickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datePickerDialog.setContentView(R.layout.date_picker_dialog);
        datePickerDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.setCancelable(true);

        LinearLayout btnCancel=datePickerDialog.findViewById(R.id.btnCancel);
        LinearLayout btnApply=datePickerDialog.findViewById(R.id.btnChange);
        TextView txtStartDate=datePickerDialog.findViewById(R.id.txtStartDate);
        TextView txtStartTitle=datePickerDialog.findViewById(R.id.txtStartTitle);
        TextView txtEndDate=datePickerDialog.findViewById(R.id.txtEndDate);
        TextView txtEndTitle=datePickerDialog.findViewById(R.id.txtEndTitle);
        LinearLayout lyStartDate=datePickerDialog.findViewById(R.id.lyStartDate);
        LinearLayout lyEndDate=datePickerDialog.findViewById(R.id.lyEndDate);

        if(selector==0)
        {
            lyStartDate.setBackgroundResource(currentPickerDrw);
            txtStartTitle.setTextColor(CrTextColor);
            txtStartDate.setText(mdy.format(pickerStart));
            txtStartDate.setTextColor(CrTextColor);

            lyEndDate.setBackgroundResource(prevPickerDrw);
            txtEndTitle.setTextColor(PrTextColor);
            txtEndDate.setText(mdy.format(pickerEnd));
            txtEndDate.setTextColor(PrTextColor);

        }else{
            lyEndDate.setBackgroundResource(currentPickerDrw);
            txtEndTitle.setTextColor(CrTextColor);
            txtEndDate.setText(mdy.format(pickerEnd));
            txtEndDate.setTextColor(CrTextColor);

            lyStartDate.setBackgroundResource(prevPickerDrw);
            txtStartTitle.setTextColor(PrTextColor);
            txtStartDate.setText(mdy.format(pickerStart));
            txtStartDate.setTextColor(PrTextColor);
        }

        dateRanger=datePickerDialog.findViewById(R.id.dateRangerView);
        dateRanger.mDateSelector=new DatePickerView.DateSelector() {
            @Override
            public void onDateSelected() {
                if(selector==0)
                {
                    pickerStart=dateRanger.color_date;
                    txtStartDate.setText(mdy.format(pickerStart));
                    if(pickerStart.after(pickerEnd))
                    {
                        pickerEnd=pickerStart;
                        txtEndDate.setText(mdy.format(pickerEnd));
                    }
                    selector=1;

                    lyEndDate.setBackgroundResource(currentPickerDrw);
                    txtEndTitle.setTextColor(CrTextColor);
                    txtEndDate.setText(mdy.format(pickerEnd));
                    txtEndDate.setTextColor(CrTextColor);

                    lyStartDate.setBackgroundResource(prevPickerDrw);
                    txtStartTitle.setTextColor(PrTextColor);
                    txtStartDate.setText(mdy.format(pickerStart));
                    txtStartDate.setTextColor(PrTextColor);
                }else{
                    pickerEnd=dateRanger.color_date;
                    if(pickerEnd.before(pickerStart))
                    {
                        pickerStart=pickerEnd;
                        txtStartDate.setText(mdy.format(pickerStart));
                    }
                    txtEndDate.setText(mdy.format(pickerEnd));
                }
                dateRanger.setRangeDate(pickerStart,pickerEnd);
            }
        };

        lyStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=0;

                lyStartDate.setBackgroundResource(currentPickerDrw);
                txtStartTitle.setTextColor(CrTextColor);
                txtStartDate.setText(mdy.format(pickerStart));
                txtStartDate.setTextColor(CrTextColor);

                lyEndDate.setBackgroundResource(prevPickerDrw);
                txtEndTitle.setTextColor(PrTextColor);
                txtEndDate.setText(mdy.format(pickerEnd));
                txtEndDate.setTextColor(PrTextColor);
            }
        });

        lyEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector=1;

                lyEndDate.setBackgroundResource(currentPickerDrw);
                txtEndTitle.setTextColor(CrTextColor);
                txtEndDate.setText(mdy.format(pickerEnd));
                txtEndDate.setTextColor(CrTextColor);

                lyStartDate.setBackgroundResource(prevPickerDrw);
                txtStartTitle.setTextColor(PrTextColor);
                txtStartDate.setText(mdy.format(pickerStart));
                txtStartDate.setTextColor(PrTextColor);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.dismiss();
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RangeSelector.OnSelect(pickerStart,pickerEnd);
                datePickerDialog.dismiss();
            }
        });
    }

    public void showPicker()
    {
        datePickerDialog.show();
    }

    public void setStartDrawable(int StartDrawable)
    {
        StartDrw=StartDrawable;
        dateRanger.SetStartDrawable(StartDrw);
        dateRanger.ApplyChanges();
    }
    public void setEndDrawable(int EndDrawable)
    {
        EndDrw=EndDrawable;
        dateRanger.SetEndDrawable(EndDrw);
        dateRanger.ApplyChanges();
    }
    public void setStartTextColor(int StartTextColor)
    {
        StartTxt=StartTextColor;
        dateRanger.SetStartTextColor(StartTxt);
        dateRanger.ApplyChanges();
    }
    public void setEndTextColor(int EndTextColor)
    {
        EndTxt=EndTextColor;
        dateRanger.SetEndTextColor(EndTxt);
        dateRanger.ApplyChanges();
    }
    public void setMidDrawable(int MidDrawable)
    {
        MidDrw=MidDrawable;
        dateRanger.SetMidDrawable(MidDrw);
        dateRanger.ApplyChanges();
    }
    public void setMidTextColor(int MidTextColor)
    {
        MidTxt=MidTextColor;
        dateRanger.SetMidTextColor(MidTxt);
        dateRanger.ApplyChanges();
    }
    public void setSingleRangeDrawable(int SingleDrawable)
    {
        SingleDrw=SingleDrawable;
        dateRanger.SetSingleRangeDrawable(SingleDrw);
        dateRanger.ApplyChanges();
    }
    public void setSingleRangeTextColor(int MidTextColor)
    {
        SingleTxt=MidTextColor;
        dateRanger.SetSingleRangeTextColor(SingleTxt);
        dateRanger.ApplyChanges();
    }
    public void setTodayDrawable(int SingleDrawable)
    {
        TodayDrw=SingleDrawable;
        dateRanger.SetTodayDrawable(TodayDrw);
        dateRanger.ApplyChanges();
    }
    public void setTodayTextColor(int MidTextColor)
    {
        TodayTxt=MidTextColor;
        dateRanger.SetTodayTextColor(TodayTxt);
        dateRanger.ApplyChanges();
    }

    public void setDateRanges(Date StartDate,Date EndDate)
    {
        dateRanger.setRangeDate(StartDate,EndDate);
        pickerStart=StartDate;
        pickerEnd=EndDate;

        txtStartDate.setText(mdy.format(pickerStart));
        txtEndDate.setText(mdy.format(pickerEnd));
    }

    public void setMaxDateRange(Date rangeMax,boolean isDisabledDateColor)
    {
        dateRanger.setMaxDate(rangeMax,isDisabledDateColor);
    }

    public void setMinDateRange(Date rangeMin,boolean isDisabledDateColor)
    {
        dateRanger.setMinDate(rangeMin,isDisabledDateColor);
    }

    public void setOnRangeSelection(OnRangeSelect rangeListener)
    {
        if(isSetRanger==0)
            isSetRanger=1;
        RangeSelector=rangeListener;
    }

    public interface OnRangeSelect
    {
        void OnSelect(Date StartDate,Date EndDate);
    }
}
