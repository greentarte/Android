package com.example.student.p211;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class loginOK extends AppCompatActivity {
    TextView txt_LoginedID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ok);
        initUI();
        Intent myIntent = getIntent();
        txt_LoginedID.setText(myIntent.getStringExtra("loginID"));
    }
    public void initUI() {
        txt_LoginedID = findViewById(R.id.txt_loginedID);
    }
}
