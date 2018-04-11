package com.example.student.p230;

import android.widget.TextView;
import java.util.Calendar;

public class TimeText implements Runnable {
    String time;
    TextView txt_time;
    Calendar cal;

    TimeText(TextView txt_time) {
        this.txt_time = txt_time;
        cal = Calendar.getInstance();
    }

    @Override
    public void run(){
        while(true) {
            try {
                Thread.sleep(1000);
                time = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE);
                txt_time.setText(time);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
