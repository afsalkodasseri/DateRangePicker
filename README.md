# DateRangePicker
Custom daterange picker for android.
PickerDialog is a Date range Picker View to allow you to pick a date range with Customized and improved UI.
You can use it within your android project and customise almost design.


## Screenshots

<p align="center">
  <img src="https://i.ibb.co/C8M38gp/Screenshot-1613641238.png" width="250" title="Selected Range">
  <img src="https://i.ibb.co/GtH2YN6/Screenshot-1613640981.png" width="250" alt="accessibility text">
</p>

## Usage

> Add dependancy in app level gradle
 
```implementation 'com.github.afsalkodasseri:DateRangePicker:1.0.0'```

> Add jitpack in Project gradle

```
allprojects {

    repositories {
    
        ...
        
        ...
        
        maven { url 'https://jitpack.io' }
        
    }
    
}
```

### Use Picker Dialog in Your Java

```
PickerDialog dialog=new PickerDialog(MainActivity.this);
dialog.showPicker();
```

or

```
Date startDate,endDate;
PickerDialog dialog=new PickerDialog(MainActivity.this,startDate,endDate);
dialog.showPicker();
```

Set On Date Range Selected

```
//(version 1.0.0)
dialog.setRangeSelected(new PickerDialog.OnRangeSelect() {
                    @Override
                    public void OnSelect(Date StartDate, Date EndDate) {
                    
                        //Do Something here
                        
                    }
                });
    
    
//(version 1.0.1) function name changed for suitable naming experience
dialog.setOnRangeSelection(new PickerDialog.OnRangeSelect() {
    @Override
    public void OnSelect(Date StartDate, Date EndDate) {

        //Do Something here

    }
});
                
                
```

                
                
                
## Customisation

> Methods to Control and Customise the Picker

```


                //Today date indicator customise with text color 
                dialog.setTodayDrawable(R.drawable.today_indicator);
                dialog.setTodayTextColor(Color.parseColor("#0000"));
                
                
                //Date Range indicator starting customise with text color 
                dialog.setStartDrawable(R.drawable.date_range_starting);
                dialog.setStartTextColor(Color.parseColor("#0000"));
                
                
                //Date Range indicator ending customise with text color 
                dialog.setEndDrawable(R.drawable.date_range_ending);
                dialog.setEndTextColor(Color.parseColor("#0000"));
                
                
                //Date Range indicator middle customise with text color 
                dialog.setMidDrawable(R.drawable.date_range_mid);
                dialog.setMidTextColor(Color.parseColor("#0000"));
                
                
                //Date Range indicator if a day is upper and lower range customise with text color 
                dialog.setSingleRangeDrawable(R.drawable.single_range_indicator);
                dialog.setSingleRangeTextColor(Color.parseColor("#0000"));
                
                
                
```


> Set Default Range as selected


```
                
                
                //setting default date range when it appears
                Date startDate,endDate;
                dialog.setDateRanges(startDate,endDate);
                
                
                
```

> Set Maximum date to be selected and minimum date to be selected

```
                
                
                //setting maximum date and minimum date
                Date minDate,maxDate;
                dialog.setMinDateRange(minDate,false);
                dialog.setMaxDateRange(maxDate,false);
                //isDisable boolean for set unselectable date text is grey
                
                >set unselectable date as grey by passing true value as second parameter in setMinDateRange or setMaxDateRange
                
```


 

happy coding


>thanks


