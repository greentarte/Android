package com.example.student.p260;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView txt_vw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_vw = findViewById(R.id.txt_vw);

    }

    public void clickBt(View v) {
        Intent intent = null;
        int requestCode=0;
        switch (v.getId()) {
            case R.id.btn2 :
                intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("num1", 1000);
                requestCode = 100;
                break;
            case R.id.btn3 :
                intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("num1", 1000);
                requestCode = 101;
                break;
        }
        /*Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        //Wraper class (java와 연동된다) key, value
        intent.putExtra("num1", 1000);
        //startActivity(intent);*/
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK) {
            int result = data.getIntExtra("result", 0);
            txt_vw.setText(result+"");

        } else if(requestCode == 101 && resultCode == RESULT_OK) {
            int result = data.getIntExtra("result", 0);
            txt_vw.setText(result+"");
        }
    }

}

