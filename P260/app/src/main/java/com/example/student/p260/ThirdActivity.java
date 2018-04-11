package com.example.student.p260;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
    Intent intent;
    int num3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        intent = getIntent();
        num3 = intent.getIntExtra("num1",0);
    }
    @Override
    protected void onStart() {
        super.onStart();
        int result = num3 * 2000;
        intent.putExtra("result",result);
        setResult(RESULT_OK, intent);
        finish();
    }
}
