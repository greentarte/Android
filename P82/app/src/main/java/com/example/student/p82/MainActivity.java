package com.example.student.p82;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4;
    boolean btnFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }
    public void initUI(){
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        btn2.setEnabled(btnFlag);
        btn3.setEnabled(btnFlag);
        btn4.setEnabled(btnFlag);

        /*btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);*/

    }
    public void startbtn(View v) {
        btn1.setText("STOP");
        btnFlag = !btnFlag;
        btn2.setEnabled(btnFlag);
        btn3.setEnabled(btnFlag);
        btn4.setEnabled(btnFlag);

        /*btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.VISIBLE);
        btn4.setVisibility(View.VISIBLE);*/
    }

    public void click1(View v) {
        Intent myIntent = new Intent(getApplicationContext(), SecondActivity.class);
        startActivity(myIntent);
    }

    public void click2(View v) {
        Intent myIntent = new Intent(getApplicationContext(), ThirdActivity.class);
        startActivity(myIntent);
    }

    public void click3(View v) {
        Intent myIntent = new Intent(getApplicationContext(), FourthActivity.class);
        startActivity(myIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show();
    }
}
