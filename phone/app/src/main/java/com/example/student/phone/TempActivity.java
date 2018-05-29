package com.example.student.phone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TempActivity extends AppCompatActivity {
    TextView setTemp,setAir;
    LinearLayout backLayout;
    int airStatus=0; //0이면 냉방 1이면 히터
    int onOff=0; //0이면 off 1이면 on
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        setTemp=findViewById(R.id.setTemp);
        setAir=findViewById(R.id.setAir);
        backLayout=findViewById(R.id.backLayout);
        button=findViewById(R.id.airControl);
    }

    public void temp_up(View v) {
        //온도 올림
        double temp =Double.parseDouble(setTemp.getText().toString())+1;
        setTemp.setText(""+temp);
    }
    public void temp_down(View v) {
        double temp =Double.parseDouble(setTemp.getText().toString())-1;
        setTemp.setText(""+temp);
    }
    public void back(View v) {
        Intent intent = new Intent(TempActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void setair(View v) {
        if(airStatus==1&&onOff==1){
            airStatus=0;
            setAir.setText("난방");

            backLayout.setBackgroundResource(R.drawable.heatphone);

        }else if(airStatus==0&&onOff==1){
            airStatus=1;
            setAir.setText("냉방");

            backLayout.setBackgroundResource(R.drawable.coolphone);
        }
    }
    public void setOnOff(View v) {
        if(onOff==0){
            onOff=1;
            button.setText("ON");

        }else if(onOff==1){
            onOff=0;
            button.setText("OFF");
            backLayout.setBackgroundResource(R.drawable.offphone);
        }
    }
}
