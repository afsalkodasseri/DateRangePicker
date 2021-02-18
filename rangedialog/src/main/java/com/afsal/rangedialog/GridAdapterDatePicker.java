package com.afsal.rangedialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GridAdapterDatePicker extends ArrayAdapter {
    private static final String TAG = "DateRanger::";
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    Date color_date,start_date,end_date;
    Context cos;
    ConstraintLayout cell_bg;
    boolean isTodayDate;
    SimpleDateFormat simpleDateFormat;
    int TodayIndicatorDrawable=R.drawable.date_square_color_out;
    int StartIndicatorDrawable=R.drawable.custom_date_start;
    int EndIndicatorDrawable=R.drawable.custom_date_end;
    int MidIndicatorDrawable=R.drawable.custom_date_range_p;
    int SingleDayIndicatorDrawable=R.drawable.date_square_color_blue;
    int TodayTextColor=Color.parseColor("#000000");
    int RangeTextColor=Color.parseColor("#FFFFFF");
    int RangeTextSingleColor=Color.parseColor("#FFFFFF");
    int RangeTextEndColor=Color.parseColor("#FFFFFF");
    int RangeMidTextColor=Color.parseColor("#000000");
    Date minDateRange,maxDateRange;
    boolean isMinDisable,isMaxDisable;
    public GridAdapterDatePicker(Context context, List<Date> monthlyDates, Calendar currentDate, Date color,Date start,Date end,int todayDrw,int todayTxt,int startDrw,int startTxt,int midDrw,int midTxt,int endDrw,int endTxt,int singleDrw,int singleTxt,Date min,boolean isMinDis,Date max,boolean isMaxDisable) {
        super(context, R.layout.single_cell);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        mInflater = LayoutInflater.from(context);
        cos=context;
        color_date=color;
        start_date=start;
        end_date=end;
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");

        TodayIndicatorDrawable=todayDrw;
        TodayTextColor=todayTxt;
        StartIndicatorDrawable=startDrw;
        RangeTextColor=startTxt;
        EndIndicatorDrawable=endDrw;
        RangeTextEndColor=endTxt;
        MidIndicatorDrawable=midDrw;
        RangeMidTextColor=midTxt;
        SingleDayIndicatorDrawable=singleDrw;
        RangeTextSingleColor=singleTxt;

        minDateRange=min;
        maxDateRange=max;
        isMinDisable=isMinDis;
        this.isMaxDisable=isMaxDisable;
    }
    @NonNull
    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        Calendar today_date= Calendar.getInstance();
        int toYear=today_date.get(Calendar.YEAR);
        int toMonth=today_date.get(Calendar.MONTH)+1;
        int toDay=today_date.get(Calendar.DATE);
        int colorday=color_date.getDate();
        int colorMonth=color_date.getMonth()+1;
        int coloryear=color_date.getYear();

        View view = convertView;
        isTodayDate=false;


        if(view == null){
            view = mInflater.inflate(R.layout.single_cell, parent, false);

        }
        TextView cellNumber = (TextView)view.findViewById(R.id.calendar_date_id);
        cell_bg=view.findViewById(R.id.cell);
        if(displayMonth == toMonth && displayYear == toYear&&dayValue==toDay){
            cell_bg.setBackgroundResource(TodayIndicatorDrawable);
            cellNumber.setTextColor(TodayTextColor);
            cellNumber.setTag(-1);
            isTodayDate=true;
        }
        if(displayMonth == currentMonth && displayYear == currentYear){
            cellNumber.setTag(0);
            cellNumber.setTextColor(TodayTextColor);
//            if(displayMonth == colorMonth&&colorday==dayValue)
//            {
////                cell_bg.setBackgroundResource(R.drawable.date_square_color_blue);
////                cellNumber.setTextColor(Color.WHITE);
//            }else{
//                Calendar eventCalendar = Calendar.getInstance();
//
//            }
            if(minDateRange!=null)
            {
                int compares=compareDates(mDate,minDateRange);
                if(isMinDisable&&compares==-1)
                {
                    cellNumber.setTextColor(Color.parseColor("#A9A9A9"));
                }
            }
            if(maxDateRange!=null)
            {
                int compares=compareDates(mDate,maxDateRange);
                if(isMaxDisable&&compares==1)
                {
                    cellNumber.setTextColor(Color.parseColor("#A9A9A9"));
                }
            }



        }else{

            if(displayMonth > currentMonth && displayYear == currentYear || (displayMonth < currentMonth && displayYear > currentYear))
            {
                cellNumber.setTag(1);
            }
            else{
                cellNumber.setTag(2);
            }

        }

        String tempItemDate=simpleDateFormat.format(mDate);
        String tempStartDate=simpleDateFormat.format(start_date);
        String tempEndDate=simpleDateFormat.format(end_date);

        if(tempItemDate.equals(tempStartDate)&&tempItemDate.equals(tempEndDate))
        {
            cell_bg.setBackgroundResource(SingleDayIndicatorDrawable);
            cellNumber.setTextColor(RangeTextSingleColor);
        }else if(tempItemDate.equals(tempStartDate)){
            view.setBackgroundResource(StartIndicatorDrawable);
            cell_bg.setBackgroundResource(StartIndicatorDrawable);
            cellNumber.setTextColor(RangeTextColor);
        }else if(tempItemDate.equals(tempEndDate)){
            view.setBackgroundResource(EndIndicatorDrawable);
            cell_bg.setBackgroundResource(EndIndicatorDrawable);
            cellNumber.setTextColor(RangeTextEndColor);
        }else if(mDate.after(start_date)&&mDate.before(end_date)){
            view.setBackgroundResource(MidIndicatorDrawable);
            cellNumber.setTextColor(RangeMidTextColor);
        }
        cellNumber.setText(String.valueOf(dayValue));
        view.setTag(position);



        return view;
    }
    @Override
    public int getCount() {
        return monthlyDates.size();
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }
    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }

    public int compareDates(Date first,Date second)
    {
        int result=-2;
        int minDay=first.getDate();
        int minMonth=first.getMonth();
        int minYear=first.getYear();

        int dayValue=second.getDate();
        int monthValue=second.getMonth();
        int yearValue=second.getYear();

        if(minYear<yearValue)
        {
            result=-1;
        }else if(minYear==yearValue)
        {
            if(minMonth<monthValue)
            {
                result=-1;
            }else if(minMonth==monthValue)
            {
                if(minDay<dayValue)
                {
                    result=-1;
                }else if(minDay==dayValue)
                {
                    result=0;
                }else {
                    result=1;
                }
            }else {
                result=1;
            }
        }else {
            result=1;
        }

        return result;
    }

}
