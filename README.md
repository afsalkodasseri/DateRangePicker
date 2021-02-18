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
 
`implementation 'com.github.afsalkodasseri:DateRangePicker:1.0.0'`

> Add jitpack in Project gradle

`allprojects {
    repositories {
        ...
        ...
        maven { url 'https://jitpack.io' }
    }
}`

### Use Picker Dialog in Your Java

`
PickerDialog dialog=new PickerDialog(MainActivity.this);
dialog.showPicker();
`

or

`
Date startDate,endDate;
PickerDialog dialog=new PickerDialog(MainActivity.this,startDate,endDate);
dialog.showPicker();`


