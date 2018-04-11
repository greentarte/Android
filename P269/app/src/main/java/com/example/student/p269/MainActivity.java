package com.example.student.p269;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBtn(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn1 :
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
                break;
            case R.id.btn2 :
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-5195-1232"));
                break;
            case R.id.btn3 :
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-5195-1232"));
                break;
        }
        startActivity(intent);
    }
}
