package com.example.student.phone;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TempActivity extends AppCompatActivity {
    Handler handler = new Handler();
    TextView setAir, setTemp;
    TextView indoorTemp;
    LinearLayout backLayout;
    int airStatus = 0; //0이면 냉방 1이면 히터
    int onOff = 0; //0이면 off 1이면 on
    Button button;

    String indoor_temp;

    //car_control테이블과 연동시 사용하는 변수
    String set_Temp;
    String set_wind;
    String set_cool;
    String set_warm;
    String set_charging_amount;
    String charging_port;
    String code;


    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        setTemp = findViewById(R.id.setTemp);
        indoorTemp = findViewById(R.id.indoorTemp);
        setAir = findViewById(R.id.setAir);
        backLayout = findViewById(R.id.backLayout);
        button = findViewById(R.id.airControl);

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //DB설정 온도 받기 시작
                                UpdateTask updateTask = new UpdateTask();
                                updateTask.execute();
                                //DB설정 온도 받기 종료

                                //DB설정 온도 삽입
                                SetTask setTask = new SetTask("http://70.12.114.148/springTest/seq=SEQ_num.NEXTVAL"+"set_temp"+set_Temp+"set_wind"+set_wind+"set_cool"+set_cool+"set_warm"+set_warm+"set_charging_amount"+set_charging_amount+"charging_port"+charging_port+"code"+code+"set_date=sysdate");
                                setTask.execute();
                                //DB설정 온도 삽입
                            }
                        });

                        Thread.sleep(5000); // 1 분마다


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

    //설정 온도 올림
    public void temp_up(View v) {

        double temp = Double.parseDouble(setTemp.getText().toString()) + 1;
        setTemp.setText("" + temp);
    }

    //설정 온도 내림
    public void temp_down(View v) {
        double temp = Double.parseDouble(setTemp.getText().toString()) - 1;
        setTemp.setText("" + temp);
    }

    //메인화면으로 이동
    public void back(View v) {
        Intent intent = new Intent(TempActivity.this, MainActivity.class);
        startActivity(intent);
        flag = false;
    }

    public void setair(View v) {
        if (airStatus == 1 && onOff == 1) {
            airStatus = 0;
            setAir.setText("난방");

            backLayout.setBackgroundResource(R.drawable.heatphone);

        } else if (airStatus == 0 && onOff == 1) {
            airStatus = 1;
            setAir.setText("냉방");

            backLayout.setBackgroundResource(R.drawable.coolphone);
        }
    }

    public void setOnOff(View v) {
        if (onOff == 0) {
            onOff = 1;
            button.setText("ON");

        } else if (onOff == 1) {
            onOff = 0;
            button.setText("OFF");
            backLayout.setBackgroundResource(R.drawable.offphone);
        }
    }

    //-----------------------------------------------------------------------------
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
                url = new URL("http://70.12.114.148/springTest/control_get.do");

                con = (HttpURLConnection) url.openConnection();
                if (con != null) {
                    con.setConnectTimeout(5000);
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "*/*");

                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String[] outResult = br.readLine().toString().split("/");

                    set_Temp = outResult[0];
                    set_cool = outResult[1];
                    set_warm = outResult[2];
                    indoor_temp = outResult[5];
                    set_charging_amount = outResult[7];
                    charging_port = outResult[8];
                    code = outResult[9];
                    set_wind = outResult[10];

                    if (set_cool.equals("1")) {
                        setAir.setText("냉방");
                    } else {
                        setAir.setText("난방");
                    }


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
            //UI change 기입

            double temp = Double.parseDouble(set_Temp);
            setTemp.setText("" + temp);
            indoorTemp.setText(indoor_temp);


        }
    }  //updateTask종료

    //------------------------------------------------------------------------------
    class SetTask extends AsyncTask<String, Void, String> {

    String url;
//    "http://70.12.114.148/springTest/setTemp.do";

        SetTask() {
        }

        SetTask(String url) {
            this.url = url;
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {


            //http request
            StringBuilder sb = new StringBuilder();
            URL url;
            HttpURLConnection con = null;
            BufferedReader reader = null;

            try {
                url = new URL(this.url);
                con = (HttpURLConnection) url.openConnection();

                if (con != null) {
                    con.setConnectTimeout(10000);   //connection 5초이상 길어지면 exepction
                    //con.setReadTimeout(10000);
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "*/*");
                    if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
                        return null;


                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = null;
                    while (true) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                    }


                }


            } catch (Exception e) {

                return e.getMessage();   //리턴하면 post로

            } finally {

                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                con.disconnect();
            }


            return sb.toString();

        }

        @Override
        protected void onPostExecute(String s) {


        }


    } //SetTask
}

