package com.afsal.dateranger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.afsal.rangedialog.PickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date tempStart=new Date(),tempEnd=new Date();
                try {
                     tempStart = new SimpleDateFormat("dd-MM-yyyy").parse("09-02-2021");
                     tempEnd = new SimpleDateFormat("dd-MM-yyyy").parse("25-12-2021");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                PickerDialog dialog=new PickerDialog(MainActivity.this);
                dialog.setDateRanges(tempStart,tempEnd);
                dialog.setMaxDateRange(tempEnd,true);
                dialog.setMinDateRange(tempStart,true);
                dialog.setMidDrawable(R.drawable.crfd);
                dialog.showPicker();
                dialog.setOnRangeSelection(new PickerDialog.OnRangeSelect() {
                    @Override
                    public void OnSelect(Date StartDate, Date EndDate) {
                        Toast.makeText(getApplicationContext(),StartDate.toString()+" :: "+EndDate.toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),EndDate.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}