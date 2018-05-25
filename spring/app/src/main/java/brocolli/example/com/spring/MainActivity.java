package brocolli.example.com.spring;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView title, range,status,temp,location;
    ImageView battery,doorlock,air;
    int door_status=0;
    int air_status=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title=findViewById(R.id.menu);
        range=findViewById(R.id.range);
        temp=findViewById(R.id.temp);
        location=findViewById(R.id.location);
        battery=findViewById(R.id.battery);
        doorlock=findViewById(R.id.doorlock);
        air=findViewById(R.id.air);


    }
    //팬모양 클릭시 변화는것
    public void air(View v){

        //작동시 이미지 색깔 변화필요
        if(air_status==0){
            air_status=1; //on으로 변경
            Toast.makeText(this, "사용자 설정온도 ON", Toast.LENGTH_SHORT).show();
            air.setImageResource(R.drawable.fan);
        }else{
            air_status=0; //off로 변경
            Toast.makeText(this, "사용자 설정온도 OFF", Toast.LENGTH_SHORT).show();
            air.setImageResource(R.drawable.wind);
        }

    }
    //프로필 클릭시 변하는 것
    public void profile(View v){
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    //자물쇠 모양 클릭시 변하는 것
    public void doorlock(View v){
        //DB에서 차량의 상태를 받아와서 변수에 저장

        if(door_status==1){
            door_status=0; //잠금으로 변경
            Toast.makeText(this, "LOCK", Toast.LENGTH_SHORT).show();
            doorlock.setImageResource(R.drawable.lock);
        }else{
            door_status=1;
            Toast.makeText(this, "UNLOCK", Toast.LENGTH_SHORT).show();
            doorlock.setImageResource(R.drawable.unlock);
        }
    }



    //온도조절 클릭시 온도제어 화면으로 전환
    public void control_temp(View v){
        Intent intent = new Intent(MainActivity.this, TempActivity.class);
        startActivity(intent);
    }
    //충전 클릭시 충전제어 화면으로 전환
    public void control_charge(View v){
        Intent intent = new Intent(MainActivity.this, ChargeActivity.class);
        startActivity(intent);
    }
    //위치 클릭시 위치 화면으로 전환
    public void display_location(View v){
        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
        startActivity(intent);
    }






}
