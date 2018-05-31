package com.example.student.phone;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ChargeActivity extends AppCompatActivity {

    Handler handler = new Handler();
    int value = 0;
    int add = 1;
    boolean flag=true;
    Button button;
    TextView range, status;
    String updateUrl = "http://70.12.114.147/ws/control_get.do";
    String available_distance = "";
    UpdateTask updateTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        button = findViewById(R.id.button);
        range = findViewById(R.id.range);
        status = findViewById(R.id.status);


        final ProgressBar progressBar = findViewById(R.id.progressBar1);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() { // Thread 로 작업할 내용을 구현
                while (flag) {
                    value = value + add;
                    if (value >= 100 || value <= 0) {
                        add = -add;
                    }
                    updateTask=new UpdateTask();
                    updateTask.execute();
                    handler.post(new Runnable() {
                        @Override
                        public void run() { // 화면에 변경하는 작업을 구현
                            progressBar.setProgress(value);
                        }
                    });

                    try {
                        Thread.sleep(100); // 시간지연
                    } catch (InterruptedException e) {
                    }
                } // end of while
            }
        });
        t.start();
    }

    public void backtoMain(View v) {
        Intent intent = new Intent(ChargeActivity.this, MainActivity.class);
        startActivity(intent);
        flag=false;
    }

    class UpdateTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            boolean result = false;
            URL url = null;
            HttpURLConnection con = null;
            BufferedReader br = null;
            try {
                url = new URL(updateUrl);

                con = (HttpURLConnection) url.openConnection();
                if (con != null) {
                    con.setConnectTimeout(5000);
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "*/*");

                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String[] outResult = br.readLine().toString().split("/");

                    available_distance = outResult[1];
                    //충전포트 열기 추가
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            range.setText(available_distance);
        }
    }
}
