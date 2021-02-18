package com.afsal.rangedialog;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DatePickerView extends LinearLayout {
    private static final String TAG ="DateRanger::";
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    public GridView calendarGridView;
//    RecyclerView calendarRecyclerView;
    private Button addEventButton;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private GridAdapterDatePicker mAdapter;
    int prev=-1,pos,cr_pos=-2;
    List<Date> dayValueInCells;
    List<String> mAdDates;
    Date selected;
    Calendar today_date= Calendar.getInstance();
    public Date color_date,startDate,endDate;
    public DateSelector mDateSelector;

    Date maxDate=null,minDate=null;
    boolean isMinDisable=false,isMaxDisable=false;
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

    public DatePickerView(Context context) {
        super(context);
    }
    public DatePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        color_date=today_date.getTime();
        startDate=new Date();
        endDate=new Date();
        if(isInEditMode())
        {
            initializeUILayout();
        }
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setGridCellClickEvents();


        Log.d(TAG, "I need to call this method");
    }
    public DatePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void initializeUILayout(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_view, this);
        previousButton = (ImageView)view.findViewById(R.id.previous_month);
        nextButton = (ImageView)view.findViewById(R.id.next_month);
        currentDate = (TextView)view.findViewById(R.id.display_current_date);
        calendarGridView = (GridView)view.findViewById(R.id.calendar_grid);
//        calendarRecyclerView=findViewById(R.id.recyCalendar);

    }
    private void setPreviousButtonClickEvent(){
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setNextButtonClickEvent(){
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }
    public void setGridCellClickEvents(){
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=(int)view.getTag();
                TextView txt=view.findViewById(R.id.calendar_date_id);
                color_date=dayValueInCells.get(pos);


                prev=pos;

                int month_id=(int)txt.getTag();
                if(month_id==-1)
                {
                    cr_pos=pos;
                }
                if(month_id==1)
                {
                    cal.add(Calendar.MONTH, 1);
//                    setUpCalendarAdapter();
                    prev=-1;
                    cr_pos=-2;

                }
                if(month_id==2)
                {
                    cal.add(Calendar.MONTH, -1);
//                    setUpCalendarAdapter();
                    prev=-1;
                    cr_pos=-2;
                }

                setUpCalendarAdapter();
                if(maxDate!=null&&minDate!=null)
                {
                    int comparerMax=compareDates(color_date,maxDate);
                    int comparerMin=compareDates(color_date,minDate);
                    if(comparerMin!=-1&&comparerMax!=1)
                    {
                        mDateSelector.onDateSelected();
                    }
                }else if(maxDate!=null)
                {
                    int comparer=compareDates(color_date,maxDate);
                    if(comparer!=1)
                    {
                        mDateSelector.onDateSelected();
                    }
                }else if(minDate!=null)
                {
                    int comparer=compareDates(color_date,minDate);
                    if(comparer!=-1)
                    {
                        mDateSelector.onDateSelected();
                    }
                }else mDateSelector.onDateSelected();
            }
        });
    }
    public void setUpCalendarAdapter(){
        dayValueInCells = new ArrayList<>();

        Calendar mCal = (Calendar)cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);
        prev=dayValueInCells.indexOf(color_date);
        cr_pos=dayValueInCells.indexOf(today_date.getTime());
        mAdapter = new GridAdapterDatePicker(context, dayValueInCells, cal, color_date,startDate,endDate,TodayDrw,TodayTxt,StartDrw,StartTxt,MidDrw,MidTxt,EndDrw,EndTxt,SingleDrw,SingleTxt,minDate,isMinDisable,maxDate,isMaxDisable);
        calendarGridView.setAdapter(mAdapter);
    }
    public void setRangeDate(Date start,Date end)
    {
        startDate=start;
        endDate=end;
        setUpCalendarAdapter();
    }

    public void setMinDate(Date start,boolean isDisabledColor)
    {
        minDate=start;
        isMinDisable=isDisabledColor;
        setUpCalendarAdapter();
    }
    public void setMaxDate(Date start,boolean isDisabledColor)
    {
        maxDate=start;
        isMaxDisable=isDisabledColor;
        setUpCalendarAdapter();
    }
    public void SetStartDrawable(int StartDrawable)
    {
        StartDrw=StartDrawable;
    }
    public void SetEndDrawable(int EndDrawable)
    {
        EndDrw=EndDrawable;
    }
    public void SetStartTextColor(int StartTextColor)
    {
        StartTxt=StartTextColor;
    }
    public void SetEndTextColor(int EndTextColor)
    {
        EndTxt=EndTextColor;
    }
    public void SetMidDrawable(int MidDrawable)
    {
        MidDrw=MidDrawable;
    }
    public void SetMidTextColor(int MidTextColor)
    {
        MidTxt=MidTextColor;
    }
    public void SetSingleRangeDrawable(int SingleDrawable)
    {
        SingleDrw=SingleDrawable;
    }
    public void SetSingleRangeTextColor(int MidTextColor)
    {
        SingleTxt=MidTextColor;
    }
    public void SetTodayDrawable(int SingleDrawable)
    {
        TodayDrw=SingleDrawable;
    }
    public void SetTodayTextColor(int MidTextColor)
    {
        TodayTxt=MidTextColor;
    }
    public void ApplyChanges()
    {
        setUpCalendarAdapter();
    }
    public interface DateSelector
    {
        void onDateSelected();
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
