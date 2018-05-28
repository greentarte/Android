package com.example.student.evproject;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ChargingActivity extends AppCompatActivity {

    Handler handler = new Handler();
    int value = 0;
    int add = 1;
    RadioGroup radioGroup;
    RadioButton onRC, offRC;
    TextView txtTime;
    int mHour, mMinute;
    String amPm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging);

        radioGroup = findViewById(R.id.radioGroup);

        txtTime=findViewById(R.id.txtTime);
        onRC = findViewById(R.id.on);
        offRC = findViewById(R.id.off);

        Calendar cal = new GregorianCalendar();
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        UpdateNow();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.on :
                        txtTime.setTextColor(Color.parseColor("#ffffff"));
                        txtTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new TimePickerDialog(ChargingActivity.this,
                                    AlertDialog.THEME_HOLO_DARK,mTimeSetListener,mHour,mMinute,false).show();
                        }
                    });
                    case R.id.off : txtTime.setTextColor(Color.parseColor("#aaaaaa"));
                }

            }
        });

        final ProgressBar progressBar = findViewById(R.id.progressBar1);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() { // Thread 로 작업할 내용을 구현
                while(true) {
                    value = value + add;
                    if (value>=100 || value<=0) {
                        add = -add;
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() { // 화면에 변경하는 작업을 구현
                            progressBar.setProgress(value);
                        }
                    });

                    try {
                        Thread.sleep(100); // 시간지연
                    } catch (InterruptedException e) {    }
                } // end of while
            }
        });
        t.start();
    }

    public void UpdateNow(){
        if(mHour > 12){
            amPm = "오후";
            mHour = mHour - 12;
        }else{
            amPm = "오전";
        }
        txtTime.setText(amPm+" "+ String.format("%d : %d",mHour,mMinute));
    }

    TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    // TODO Auto-generated method stub
                    //사용자가 입력한 값을 가져온뒤
                    mHour = hourOfDay;
                    mMinute = minute;

                    //텍스트뷰의 값을 업데이트함
                    UpdateNow();

                }
            };
}
