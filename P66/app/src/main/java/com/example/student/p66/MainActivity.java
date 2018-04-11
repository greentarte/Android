package com.example.student.p66;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn1click(View v) {
        //Toast.makeText(this, "btn1", Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
        startActivity(myIntent);
    }
    public void btn2click(View v) {
        Intent myIntent = new Intent(getApplicationContext(), testActivity.class);
        startActivity(myIntent);
    }
    public void btn3click(View v) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-5195-1232"));
        startActivity(myIntent);
    }

}
