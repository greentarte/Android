package com.example.student.p221;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Resources res;
    ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }
    public void initUI() {
        res = getResources();
        img1 = findViewById(R.id.img1);
    }
    public void clickBtn(View v) {
        BitmapDrawable bitmap=null;
        switch(v.getId()) {
            case R.id.btn1 :
                bitmap = (BitmapDrawable) res.getDrawable(R.drawable.bg5);
                break;
            case R.id.btn2 :
                bitmap = (BitmapDrawable) res.getDrawable(R.drawable.bg6);
                break;
            case R.id.btn3 :
                bitmap = (BitmapDrawable) res.getDrawable(R.drawable.bg7);
                break;
                default: break;
        }
/*        if(v.getId()==R.id.btn1){
            bitmap = (BitmapDrawable) res.getDrawable(R.drawable.bg5);
        }else if(v.getId()==R.id.btn2){
            bitmap = (BitmapDrawable) res.getDrawable(R.drawable.bg6);
        }else if(v.getId()==R.id.btn3){
            bitmap = (BitmapDrawable) res.getDrawable(R.drawable.bg7);
        }*/
        img1.setImageDrawable(bitmap);
    }
}
