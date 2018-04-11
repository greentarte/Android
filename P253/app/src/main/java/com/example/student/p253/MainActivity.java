package com.example.student.p253;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LinearLayout container;
    LayoutInflater inflater;
    TextView sub_txt_vw1;
    Button sub_btn1, sub_btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }
    public void initUI() {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        container = findViewById(R.id.container);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void onClickBtn(View v) {
        inflater.inflate(R.layout.sub, container, true);
        sub_txt_vw1 = container.findViewById(R.id.sub_txt_vw1);
        sub_txt_vw1.setText("Button Click");
        sub_btn1 = container.findViewById(R.id.sub_btn1);
        sub_btn2 = container.findViewById(R.id.sub_btn2);
        sub_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout container1 = findViewById(R.id.container);
                inflater.inflate(R.layout.sub2, container1, true);
            }
        });
        sub_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout container2 = findViewById(R.id.container);
                inflater.inflate(R.layout.sub3, container2, true);
            }
        });
    }

}
