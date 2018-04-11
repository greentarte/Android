package com.example.student.p246;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView img_vw1, img_vw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    public void initUI() {
        img_vw1 = findViewById(R.id.img_vw1);
        img_vw2 = findViewById(R.id.img_vw2);
    }

    public void onClickBtn(View v) {
        switch(v.getId()){
            case R.id.btn1 :
                if(img_vw2.getDrawable() != null) {
                    img_vw1.setImageDrawable(img_vw2.getDrawable());
                    img_vw2.setImageDrawable(null);
                }
                break;
            case R.id.btn2 :
                if(img_vw1.getDrawable() != null) {
                    img_vw2.setImageDrawable(img_vw1.getDrawable());
                    img_vw1.setImageDrawable(null);
                }
                break;
        }
    }
}
